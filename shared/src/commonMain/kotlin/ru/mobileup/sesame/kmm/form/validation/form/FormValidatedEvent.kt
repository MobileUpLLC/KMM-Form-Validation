package ru.mobileup.sesame.kmm.form.validation.form

/**
 * Informs that a form was validated.
 */
class FormValidatedEvent(
    val result: FormValidationResult,
    val displayResult: Boolean
)