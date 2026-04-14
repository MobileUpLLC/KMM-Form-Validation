package ru.mobileup.kmm_form_validation.android_sample.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.mobileup.kmm_form_validation.android_sample.R
import ru.mobileup.kmm_form_validation.sharedsample.ui.Gender
import ru.mobileup.kmm_form_validation.sharedsample.ui.SampleValidationError
import ru.mobileup.kmm_form_validation.validation.control.ValidationError

@Composable
fun Gender.displayName(): String = stringResource(
    when (this) {
        Gender.Male -> R.string.gender_male
        Gender.Female -> R.string.gender_female
        Gender.Other -> R.string.gender_other
    }
)

@Composable
fun ValidationError.asString(): String = stringResource(
    when (this) {
        SampleValidationError.BlankField -> R.string.field_is_blank_error_message
        SampleValidationError.InvalidEmail -> R.string.invalid_email_error_message
        SampleValidationError.InvalidPhone -> R.string.invalid_phone_error_message
        SampleValidationError.InvalidPassword -> R.string.invalid_password_error_message
        SampleValidationError.PasswordsDoNotMatch -> R.string.passwords_do_not_match_error_message
        SampleValidationError.TermsNotAccepted -> R.string.terms_are_accepted_error_message
        else -> error("Unsupported validation error: $this")
    }
)
