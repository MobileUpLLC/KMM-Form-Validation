package ru.mobileup.kmm_form_validation.sharedsample.ui

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.kmm_form_validation.options.ImeAction
import ru.mobileup.kmm_form_validation.options.KeyboardCapitalization
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType
import ru.mobileup.kmm_form_validation.options.OnlyDigitsTextTransformation
import ru.mobileup.kmm_form_validation.sharedsample.utils.CheckControl
import ru.mobileup.kmm_form_validation.sharedsample.utils.InputControl
import ru.mobileup.kmm_form_validation.sharedsample.utils.PickerControl
import ru.mobileup.kmm_form_validation.sharedsample.utils.componentScope
import ru.mobileup.kmm_form_validation.sharedsample.utils.computed
import ru.mobileup.kmm_form_validation.sharedsample.utils.formValidator
import ru.mobileup.kmm_form_validation.validation.control.equalsTo
import ru.mobileup.kmm_form_validation.validation.control.isNotBlank
import ru.mobileup.kmm_form_validation.validation.control.isPicked
import ru.mobileup.kmm_form_validation.validation.control.regex
import ru.mobileup.kmm_form_validation.validation.control.validation
import ru.mobileup.kmm_form_validation.validation.form.RevalidateOnValueChanged
import ru.mobileup.kmm_form_validation.validation.form.SetFocusOnFirstInvalidControlAfterValidation
import ru.mobileup.kmm_form_validation.validation.form.ValidateOnFocusLost
import ru.mobileup.kmm_form_validation.validation.form.checked

enum class SubmitButtonState {
    Valid,
    Invalid
}

class RealFormComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext, FormComponent {

    companion object {
        private const val NAME_MAX_LENGTH = 30
        private const val PHONE_MAX_LENGTH = 10
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 20
        private val PASSWORD_RANGE = PASSWORD_MIN_LENGTH..PASSWORD_MAX_LENGTH
        private const val PASSWORD_SPEC_CHARS = "!@#$%^&*_-+"
        private const val RUS_PHONE_DIGIT_COUNT = 10
        private const val EMAIL_REGEX_PATTERN =
            "[a-zA-Z0-9+._%\\-]{1,256}@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
    }

    override val nameInput = InputControl(
        maxLength = NAME_MAX_LENGTH,
        textTransformation = OnlyLettersTextTransformation,
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
        textTransformation = OnlyDigitsTextTransformation()
    )

    override val passwordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next,
            autoCorrect = false
        )
    )

    override val confirmPasswordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            autoCorrect = false
        )
    )

    override val termsCheckBox = CheckControl()

    override val newsletterCheckBox = CheckControl()

    override val genderPicker = PickerControl<Gender>()

    override val showConfetti = MutableStateFlow(false)

    private val formValidator = formValidator {

        features = listOf(
            ValidateOnFocusLost,
            RevalidateOnValueChanged,
            SetFocusOnFirstInvalidControlAfterValidation
        )

        input(nameInput) {
            isNotBlank(SampleValidationError.BlankField)
        }

        picker(genderPicker) {
            isPicked(SampleValidationError.BlankField)
        }

        input(emailInput, required = false) {
            isNotBlank(SampleValidationError.BlankField)
            regex(
                regex = EMAIL_REGEX_PATTERN.toRegex(),
                errorMessage = SampleValidationError.InvalidEmail
            )
        }

        input(phoneInput) {
            isNotBlank(SampleValidationError.BlankField)
            validation(
                isValid = { it.length == RUS_PHONE_DIGIT_COUNT },
                errorMessage = SampleValidationError.InvalidPhone
            )
        }

        input(passwordInput) {
            isNotBlank(SampleValidationError.BlankField)
            validation(
                isValid = ::isPasswordValid,
                errorMessage = SampleValidationError.InvalidPassword
            )
        }

        input(confirmPasswordInput) {
            isNotBlank(SampleValidationError.BlankField)
            equalsTo(
                inputControl = passwordInput,
                errorMessage = SampleValidationError.PasswordsDoNotMatch
            )
        }

        checked(termsCheckBox, SampleValidationError.TermsNotAccepted)
    }

    private fun isPasswordValid(password: String): Boolean = password.run {
        val containsDigit = any(Char::isDigit)
        val containsLowercase = any(Char::isLowerCase)
        val containsUppercase = any(Char::isUpperCase)
        val containsSpecChar = any { it in PASSWORD_SPEC_CHARS }
        val notContainsInvalidChar = !any { !it.isLetterOrDigit() && it !in PASSWORD_SPEC_CHARS }
        val validLength = length in PASSWORD_RANGE

        containsDigit && containsLowercase && containsUppercase && containsSpecChar && notContainsInvalidChar && validLength
    }

    override val submitButtonState = computed(
        formValidator.isFilledState,
        formValidator.hasErrorState
    ) { isFilled, hasError ->
        if (isFilled && !hasError) SubmitButtonState.Valid else SubmitButtonState.Invalid
    }

    init {
        formValidator.validationState
            .onEach {
                if (it.isInvalid) {
                    showConfetti.value = false
                }
            }
            .launchIn(componentScope)
    }

    override fun onSubmitClicked() {
        showConfetti.value = formValidator.validate().isValid
    }
}
