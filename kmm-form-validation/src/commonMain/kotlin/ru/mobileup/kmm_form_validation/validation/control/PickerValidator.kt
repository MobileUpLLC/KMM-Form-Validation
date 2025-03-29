package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.PickerControl
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder

/**
 * Validator for [PickerControl] that ensures a selection-based control meets validation requirements.
 * Use [FormValidatorBuilder.picker] for convenient DSL-based creation.
 *
 * @param T The type of value managed by the picker.
 * @param required Specifies whether a value must be selected for validation to pass.
 * @param validations A list of validation functions applied sequentially until the first error is found.
 *
 */
class PickerValidator<T>(
    override val control: PickerControl<T>,
    val required: Boolean = true,
    private val validations: List<(T?) -> ValidationResult>,
) : BaseValidator<T?, PickerControl<T>>(control) {

    override fun performValidation(): ValidationResult {
        if (control.skipInValidation.value) return ValidationResult.Skipped
        if (control.valueState.value == null && !required) return ValidationResult.Valid

        validations.forEach { validation ->
            val result = validation(control.valueState.value)
            if (result is ValidationResult.Invalid) return result
        }

        return ValidationResult.Valid
    }

    /**
     * Indicates whether a value is selected in the picker.
     * A value is considered selected if it's non-null and required.
     */
    override val isFilled: Boolean
        get() = if (required) control.valueState.value != null else true
}
