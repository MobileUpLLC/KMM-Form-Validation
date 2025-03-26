package ru.mobileup.kmm_form_validation.validation.form

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.kmm_form_validation.control.ValidatableControl
import ru.mobileup.kmm_form_validation.util.computed
import ru.mobileup.kmm_form_validation.validation.control.CheckValidator
import ru.mobileup.kmm_form_validation.validation.control.ControlValidator
import ru.mobileup.kmm_form_validation.validation.control.InputValidator
import ru.mobileup.kmm_form_validation.validation.control.ValidationResult

/**
 * Validator for multiple controls.
 *
 * Use [formValidator] to create it with a handy DSL.
 */
class FormValidator(
    val validators: Map<ValidatableControl<*>, ControlValidator<*>>,
    val coroutineScope: CoroutineScope,
) {
    private val mutableValidatedEventFlow = MutableSharedFlow<FormValidatedEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * Emits [FormValidatedEvent] after each validation.
     */
    val validatedEventFlow get() = mutableValidatedEventFlow.asSharedFlow()

    /**
     * Validates controls.
     * @param displayResult specifies if a result will be displayed on UI.
     */
    fun validate(displayResult: Boolean = true): FormValidationResult {
        val results = mutableMapOf<ValidatableControl<*>, ValidationResult>()
        validators.forEach { (control, validator) ->
            results[control] = validator.validate(displayResult)
        }

        return FormValidationResult(results).also {
            mutableValidatedEventFlow.tryEmit(FormValidatedEvent(it, displayResult))
        }
    }

    /**
     * Emits a state flow with the current form validation result.
     * Updates whenever a control's value or `skipInValidation` state changes.
     */
    val validationState: StateFlow<FormValidationResult> by lazy {
        MutableStateFlow(validate(displayResult = false)).apply {
            validators.forEach { (control, _) ->
                onControlStateChanged(coroutineScope, control.value, control.skipInValidation) {
                    value = validate(displayResult = false)
                }
            }
        }
    }

    /**
     * Checks whether all required fields in the form are filled.
     * A field is considered filled if:
     * - It is an [InputValidator] with `required = true` and contains non-blank text.
     * - It is a [CheckValidator] and is checked.
     * - It is skipped in validation.
     */
    val isFilled: Boolean
        get() = validators.values.all { validator ->
            if (validator.control.skipInValidation.value) return@all true

            when (validator) {
                is InputValidator -> if (validator.required) validator.control.text.value.isNotBlank() else true
                is CheckValidator -> validator.control.checked.value
                else -> true
            }
        }

    /**
     * Emits a state flow indicating whether the form is completely filled.
     * Updates whenever a control's value or `skipInValidation` state changes.
     */
    val isFilledState: StateFlow<Boolean> by lazy {
        MutableStateFlow(isFilled).apply {
            validators.forEach { (control, _) ->
                onControlStateChanged(coroutineScope, control.value, control.skipInValidation) {
                    value = isFilled
                }
            }
        }
    }

    /**
     * Checks whether the form contains any validation errors.
     * A form has errors if any control has a non-null error and is not skipped in validation.
     */
    val FormValidator.hasError: Boolean
        get() = validators.keys.any { it.error.value != null && !it.skipInValidation.value }

    /**
     * Emits a state flow indicating whether the form has any validation errors.
     * Updates whenever a control's error state or `skipInValidation` changes.
     */
    val hasErrorState: StateFlow<Boolean> by lazy {
        MutableStateFlow(hasError).apply {
            validators.forEach { (control, _) ->
                onControlStateChanged(coroutineScope, control.error, control.skipInValidation) {
                    value = hasError
                }
            }
        }
    }

    private fun <T> onControlStateChanged(
        coroutineScope: CoroutineScope,
        state: StateFlow<T>,
        skipInValidation: StateFlow<Boolean>,
        callback: () -> Unit,
    ) {
        computed(coroutineScope, state, skipInValidation) { v1, v2 -> v1 to v2 }
            .drop(1)
            .onEach { callback() }
            .launchIn(coroutineScope)
    }
}
