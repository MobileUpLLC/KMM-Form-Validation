package ru.mobileup.sesame.kmm_form_validation.form.validation.form

import ru.mobileup.sesame.kmm_form_validation.form.control.ValidatableControl
import ru.mobileup.sesame.kmm_form_validation.form.validation.control.ValidationResult

/**
 * Represents result of form validation.
 */
data class FormValidationResult(
    val controlResults: Map<ValidatableControl<*>, ValidationResult>
) {

    val isValid get() = controlResults.values.none { it is ValidationResult.Invalid }
}