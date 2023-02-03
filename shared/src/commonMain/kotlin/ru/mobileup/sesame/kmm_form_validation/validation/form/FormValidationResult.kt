package ru.mobileup.sesame.kmm_form_validation.validation.form

import ru.mobileup.sesame.kmm_form_validation.control.ValidatableControl
import ru.mobileup.sesame.kmm_form_validation.validation.control.ValidationResult

/**
 * Represents result of form validation.
 */
data class FormValidationResult(
    val controlResults: Map<ValidatableControl<*>, ValidationResult>
) {

    val isValid get() = controlResults.values.none { it is ValidationResult.Invalid }
}