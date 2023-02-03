package ru.mobileup.sesame.kmm_form_validation.form.validation.form

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.mobileup.sesame.kmm_form_validation.form.control.ValidatableControl
import ru.mobileup.sesame.kmm_form_validation.form.validation.control.ControlValidator
import ru.mobileup.sesame.kmm_form_validation.form.validation.control.ValidationResult

/**
 * Validator for multiple controls.
 *
 * Use [formValidator] to create it with a handy DSL.
 */
class FormValidator(
    val validators: Map<ValidatableControl<*>, ControlValidator<*>>
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