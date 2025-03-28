package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder
import ru.mobileup.kmm_form_validation.validation.form.checked

/**
 * Validator for [CheckControl].
 * @param validation implements validation logic.
 * @param showError a callback that is called to show one-time error such as a toast. For permanent errors use [CheckControl.error] state.
 *
 * Use [FormValidatorBuilder.checked] to create it with a handy DSL.
 */
class CheckValidator(
    override val control: CheckControl,
    private val validation: (Boolean) -> ValidationResult,
    private val showError: ((StringDesc) -> Unit)? = null,
) : ControlValidator<CheckControl> {

    override fun validate(displayResult: Boolean): ValidationResult {
        return getValidationResult().also {
            if (displayResult) displayValidationResult(it)
        }
    }

    private fun getValidationResult(): ValidationResult {
        if (control.skipInValidation.value) {
            return ValidationResult.Skipped
        }

        return validation(control.value.value)
    }

    private fun displayValidationResult(
        validationResult: ValidationResult,
    ) = when (validationResult) {
        is ValidationResult.Invalid -> {
            control.error.value = validationResult.errorMessage
            showError?.invoke(validationResult.errorMessage)
        }

        else -> control.error.value = null
    }
}
