package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder

/**
 * Validator for [InputControl].
 * @param required specifies if blank input is considered valid.
 * @param validations a list of functions that implements validation logic. Validations are processed sequentially until first error.
 *
 * Use [FormValidatorBuilder.input] to create it with a handy DSL.
 */
class InputValidator(
    override val control: InputControl,
    val required: Boolean = true,
    private val validations: List<(String) -> ValidationResult>,
) : ControlValidator<InputControl> {

    override fun validate(displayResult: Boolean): ValidationResult {
        return getValidationResult().also {
            if (displayResult) displayValidationResult(it)
        }
    }

    private fun getValidationResult(): ValidationResult {
        if (control.skipInValidation.value) {
            return ValidationResult.Skipped
        }

        if (control.value.value.isBlank() && !required) {
            return ValidationResult.Valid
        }

        validations.forEach { validation ->
            val result = validation(control.value.value)
            if (result is ValidationResult.Invalid) {
                return result
            }
        }

        return ValidationResult.Valid
    }

    private fun displayValidationResult(
        validationResult: ValidationResult,
    ) = when (validationResult) {
        is ValidationResult.Invalid -> control.error.value = validationResult.errorMessage
        else -> control.error.value = null
    }
}
