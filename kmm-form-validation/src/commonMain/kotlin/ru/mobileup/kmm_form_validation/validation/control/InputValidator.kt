package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder

/**
 * Validator for [InputControl] that ensures input meets specified requirements.
 * Use [FormValidatorBuilder.input] to create it with a handy DSL.
 *
 * @param required Determines if blank input is considered invalid. If `true`, an empty input will fail validation.
 * @param validations A list of validation functions applied sequentially until the first error is found.
 */
class InputValidator(
    override val control: InputControl,
    val required: Boolean = true,
    private val validations: List<(String) -> ValidationResult>,
) : BaseValidator<String, InputControl>(control) {

    override fun performValidation(): ValidationResult {
        if (control.skipInValidation.value) return ValidationResult.Skipped
        if (control.valueState.value.isBlank() && !required) return ValidationResult.Valid

        validations.forEach { validation ->
            val result = validation(control.valueState.value)
            if (result is ValidationResult.Invalid) return result
        }

        return ValidationResult.Valid
    }

    /**
     * Indicates whether the input field has been filled.
     * If the field is required, it must not be blank.
     */
    override val isFilled: Boolean
        get() = if (required) control.valueState.value.isNotBlank() else true
}
