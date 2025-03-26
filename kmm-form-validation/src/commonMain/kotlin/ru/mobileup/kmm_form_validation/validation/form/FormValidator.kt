package ru.mobileup.kmm_form_validation.validation.form

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.mobileup.kmm_form_validation.control.ValidatableControl
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
}

/**
 * Checks whether all required fields in the form are filled.
 * A field is considered filled if:
 * - It is an [InputValidator] with `required = true` and contains non-blank text.
 * - It is a [CheckValidator] and is checked.
 * - It is skipped in validation.
 */
val FormValidator.isFilled: Boolean
    get() = validators.values.all { validator ->
        if (validator.control.skipInValidation.value) return@all true

        when (validator) {
            is InputValidator -> if (validator.required) validator.control.text.value.isNotBlank() else true
            is CheckValidator -> validator.control.checked.value
            else -> true
        }
    }

/**
 * Checks whether the form contains any validation errors.
 * A form has errors if any control has a non-null error and is not skipped in validation.
 */
val FormValidator.hasError: Boolean
    get() = validators.keys.any { it.error.value != null && !it.skipInValidation.value }
