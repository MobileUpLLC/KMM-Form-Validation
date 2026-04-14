package ru.mobileup.kmm_form_validation.sharedsample.ui

import ru.mobileup.kmm_form_validation.validation.control.ValidationError

enum class SampleValidationError : ValidationError {
    BlankField,
    InvalidEmail,
    InvalidPhone,
    InvalidPassword,
    PasswordsDoNotMatch,
    TermsNotAccepted,
}
