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
import ru.mobileup.kmm_form_validation.control.UIControl
import ru.mobileup.kmm_form_validation.util.computed
import ru.mobileup.kmm_form_validation.validation.control.ControlValidator
import ru.mobileup.kmm_form_validation.validation.control.ValidationResult

/**
 * Validator for multiple controls.
 *
 * Use [formValidator] to create it with a handy DSL.
 */
class FormValidator(
    val validators: Map<UIControl<*>, ControlValidator<*>>,
    val coroutineScope: CoroutineScope,
) {

    private val mutableValidatedEventFlow = MutableSharedFlow<FormValidatedEvent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * Emits [FormValidatedEvent] after each validation.
     */
    val validatedEventFlow = mutableValidatedEventFlow.asSharedFlow()

    /**
     * Validates controls.
     * @param displayResult specifies if a result will be displayed on UI.
     */
    fun validate(displayResult: Boolean = true): FormValidationResult {
        val results = mutableMapOf<UIControl<*>, ValidationResult>()
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
            validators.keys.forEach { control ->
                onControlStateChanged(control.value, control.skipInValidation) {
                    value = validate(displayResult = false)
                }
            }
        }
    }

    /**
     * Checks whether all required fields in the form are filled.
     */
    val isFilled: Boolean
        get() = validators.values.all { validator ->
            if (validator.control.skipInValidation.value) return@all true
            validator.isFilled
        }

    /**
     * Emits a state flow indicating whether the form is completely filled.
     * Updates whenever a control's value or `skipInValidation` state changes.
     */
    val isFilledState: StateFlow<Boolean> by lazy {
        MutableStateFlow(isFilled).apply {
            validators.keys.forEach { control ->
                onControlStateChanged(control.value, control.skipInValidation) {
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
            validators.keys.forEach { control ->
                onControlStateChanged(control.error, control.skipInValidation) {
                    value = hasError
                }
            }
        }
    }

    private fun <T> onControlStateChanged(
        state: StateFlow<T>,
        skipInValidation: StateFlow<Boolean>,
        action: (Pair<T, Boolean>) -> Unit,
    ) {
        computed(coroutineScope, state, skipInValidation) { v1, v2 -> v1 to v2 }
            .drop(1)
            .onEach(action)
            .launchIn(coroutineScope)
    }
}
