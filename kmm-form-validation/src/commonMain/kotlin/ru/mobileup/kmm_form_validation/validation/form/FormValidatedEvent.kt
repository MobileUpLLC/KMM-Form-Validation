package ru.mobileup.kmm_form_validation.validation.form

/**
 * Informs that a form was validated.
 */
class FormValidatedEvent(
    val result: FormValidationResult,
    val displayResult: Boolean
)