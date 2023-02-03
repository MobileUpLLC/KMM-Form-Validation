package ru.mobileup.sesame.kmm_form_validation.form.validation.form

/**
 * Informs that a form was validated.
 */
class FormValidatedEvent(
    val result: FormValidationResult,
    val displayResult: Boolean
)