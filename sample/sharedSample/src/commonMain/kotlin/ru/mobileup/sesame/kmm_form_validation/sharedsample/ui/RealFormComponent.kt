package ru.mobileup.sesame.kmm_form_validation.sharedsample.ui

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mobileup.sesame.kmm_form_validation.control.PasswordVisualTransformation
import ru.mobileup.sesame.kmm_form_validation.options.ImeAction
import ru.mobileup.sesame.kmm_form_validation.options.KeyboardCapitalization
import ru.mobileup.sesame.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.sesame.kmm_form_validation.options.KeyboardType
import ru.mobileup.sesame.kmm_form_validation.sample.Res
import ru.mobileup.sesame.kmm_form_validation.sharedsample.utils.CheckControl
import ru.mobileup.sesame.kmm_form_validation.sharedsample.utils.InputControl
import ru.mobileup.sesame.kmm_form_validation.sharedsample.utils.componentCoroutineScope
import ru.mobileup.sesame.kmm_form_validation.sharedsample.utils.computed
import ru.mobileup.sesame.kmm_form_validation.validation.control.*
import ru.mobileup.sesame.kmm_form_validation.validation.form.*

enum class SubmitButtonState {
    Valid,
    Invalid
}

class RealFormComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, FormComponent {

    companion object {
        private const val NAME_MAX_LENGTH = 30
        private const val PHONE_MAX_LENGTH = 10
        private const val PASSWORD_MIN_LENGTH = 6
        private const val RUS_PHONE_DIGIT_COUNT = 10
        private const val EMAIL_REGEX_PATTERN =
            "[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    }

    private val coroutineScope = componentCoroutineScope()

    override val nameInput = InputControl(
        maxLength = NAME_MAX_LENGTH,
        textTransformation = {
            it.replace(Regex("[1234567890+=]"), "")
        },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        )
    )

    override val emailInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        )
    )

    override val phoneInput = InputControl(
        maxLength = PHONE_MAX_LENGTH,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        textTransformation = { it.replace(Regex("[^1234567890(-)+]"), "") },
        visualTransformation = RussianPhoneNumberVisualTransformation
    )

    override val passwordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        visualTransformation = PasswordVisualTransformation()
    )

    override val confirmPasswordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        visualTransformation = PasswordVisualTransformation()
    )

    override val termsCheckBox = CheckControl()

    private val dropKonfettiChannel = Channel<Unit>(Channel.UNLIMITED)

    override val dropKonfettiEvent = dropKonfettiChannel.receiveAsFlow()

    private val formValidator = coroutineScope.formValidator {

        features = listOf(
            ValidateOnFocusLost,
            RevalidateOnValueChanged,
            SetFocusOnFirstInvalidControlAfterValidation
        )

        input(nameInput) {
            isNotBlank(Res.strings.field_is_blank_error_message)
        }

        input(emailInput, required = false) {
            isNotBlank(Res.strings.field_is_blank_error_message)
            regex(
                regex = EMAIL_REGEX_PATTERN.toRegex(),
                errorMessageRes = Res.strings.invalid_email_error_message
            )
        }

        input(phoneInput) {
            isNotBlank(Res.strings.field_is_blank_error_message)
            validation(
                { str ->
                    str.count { it.isDigit() } == RUS_PHONE_DIGIT_COUNT
                },
                Res.strings.invalid_phone_error_message
            )
        }

        input(passwordInput) {
            isNotBlank(Res.strings.field_is_blank_error_message)
            minLength(
                PASSWORD_MIN_LENGTH,
                StringDesc.ResourceFormatted(
                    Res.strings.min_length_error_message,
                    PASSWORD_MIN_LENGTH
                )
            )
            validation(
                { str -> str.any { it.isDigit() } },
                Res.strings.must_contain_digit_error_message
            )
        }

        input(confirmPasswordInput) {
            isNotBlank(Res.strings.field_is_blank_error_message)
            equalsTo(
                passwordInput,
                Res.strings.passwords_do_not_match_error_message
            )
        }

        checked(termsCheckBox, Res.strings.terms_are_accepted_error_message)
    }

    private val dynamicResult = coroutineScope.dynamicValidationResult(formValidator)

    override val submitButtonState = computed(coroutineScope, dynamicResult) { result ->
        if (result.isValid) SubmitButtonState.Valid else SubmitButtonState.Invalid
    }

    override fun onSubmitClicked() {
        val result = formValidator.validate()
        if (result.isValid) {
            dropKonfettiChannel.trySend(Unit)
        }
    }
}