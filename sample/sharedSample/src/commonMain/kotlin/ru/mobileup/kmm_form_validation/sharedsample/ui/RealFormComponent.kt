package ru.mobileup.kmm_form_validation.sharedsample.ui

import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.kmm_form_validation.options.ImeAction
import ru.mobileup.kmm_form_validation.options.KeyboardCapitalization
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType
import ru.mobileup.kmm_form_validation.sharedsample.MR
import ru.mobileup.kmm_form_validation.sharedsample.utils.*
import ru.mobileup.kmm_form_validation.validation.control.*
import ru.mobileup.kmm_form_validation.validation.form.RevalidateOnValueChanged
import ru.mobileup.kmm_form_validation.validation.form.SetFocusOnFirstInvalidControlAfterValidation
import ru.mobileup.kmm_form_validation.validation.form.ValidateOnFocusLost
import ru.mobileup.kmm_form_validation.validation.form.checked

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
        textTransformation = { text -> text.filter { it.isDigit() } },
        visualTransformation = RussianPhoneNumberVisualTransformation
    )

    override val passwordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        )
    )

    override val confirmPasswordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )

    override val termsCheckBox = CheckControl()

    override val valid = MutableStateFlow(false)

    private val formValidator = formValidator {

        features = listOf(
            ValidateOnFocusLost,
            RevalidateOnValueChanged,
            SetFocusOnFirstInvalidControlAfterValidation
        )

        input(nameInput) {
            isNotBlank(MR.strings.field_is_blank_error_message)
        }

        input(emailInput, required = false) {
            isNotBlank(MR.strings.field_is_blank_error_message)
            regex(
                regex = EMAIL_REGEX_PATTERN.toRegex(),
                errorMessageRes = MR.strings.invalid_email_error_message
            )
        }

        input(phoneInput) {
            isNotBlank(MR.strings.field_is_blank_error_message)
            validation(
                { str ->
                    str.count { it.isDigit() } == RUS_PHONE_DIGIT_COUNT
                },
                MR.strings.invalid_phone_error_message
            )
        }

        input(passwordInput) {
            isNotBlank(MR.strings.field_is_blank_error_message)
            minLength(
                PASSWORD_MIN_LENGTH,
                StringDesc.ResourceFormatted(
                    MR.strings.min_length_error_message,
                    PASSWORD_MIN_LENGTH
                )
            )
            validation(
                { str -> str.any { it.isDigit() } },
                MR.strings.must_contain_digit_error_message
            )
        }

        input(confirmPasswordInput) {
            isNotBlank(MR.strings.field_is_blank_error_message)
            equalsTo(
                passwordInput,
                MR.strings.passwords_do_not_match_error_message
            )
        }

        checked(termsCheckBox, MR.strings.terms_are_accepted_error_message)
    }

    private val dynamicResult = dynamicValidationResult(formValidator)

    override val submitButtonState = computed(dynamicResult) { result ->
        if (result.isValid) SubmitButtonState.Valid else SubmitButtonState.Invalid
    }

    init {
        dynamicResult
            .onEach {
                if (!it.isValid) {
                    valid.value = false
                }
            }
            .launchIn(componentScope)
    }

    override fun onSubmitClicked() {
        valid.value = formValidator.validate().isValid
    }
}