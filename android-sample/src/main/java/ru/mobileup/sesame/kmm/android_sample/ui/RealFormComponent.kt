package ru.mobileup.sesame.kmm.android_sample.ui

import android.util.Patterns
import androidx.annotation.ColorRes
import com.arkivanov.decompose.ComponentContext
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.mobileup.sesame.kmm.android_sample.utils.computed
import ru.mobileup.sesame.kmm.android_sample.R
import ru.mobileup.sesame.kmm.android_sample.utils.CheckControl
import ru.mobileup.sesame.kmm.android_sample.utils.InputControl
import ru.mobileup.sesame.kmm.android_sample.utils.componentCoroutineScope
import ru.mobileup.sesame.kmm.form.options.ImeAction
import ru.mobileup.sesame.kmm.form.options.KeyboardCapitalization
import ru.mobileup.sesame.kmm.form.options.KeyboardOptions
import ru.mobileup.sesame.kmm.form.options.KeyboardType
import ru.mobileup.sesame.kmm.form.validation.control.*
import ru.mobileup.sesame.kmm.form.validation.form.*

enum class SubmitButtonState(@ColorRes val color: Int) {
    Valid(R.color.green),
    Invalid(R.color.red)
}

class RealFormComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, FormComponent {

    companion object {
        private const val NAME_MAX_LENGTH = 30
        private const val PHONE_MAX_LENGTH = 10
        private const val PASSWORD_MIN_LENGTH = 6
        private const val RUS_PHONE_DIGIT_COUNT = 10
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
        )
    )

    override val confirmPasswordInput = InputControl(
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
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
            isNotBlank(StringResource(R.string.field_is_blank_error_message))
        }

        input(emailInput, required = false) {
            isNotBlank(StringResource(R.string.field_is_blank_error_message))
            regex(
                Patterns.EMAIL_ADDRESS.toRegex(),
                StringResource(R.string.invalid_email_error_message)
            )
        }

        input(phoneInput) {
            isNotBlank(StringResource(R.string.field_is_blank_error_message))
            validation(
                { str ->
                    str.count { it.isDigit() } == RUS_PHONE_DIGIT_COUNT
                },
                StringResource(R.string.invalid_phone_error_message)
            )
        }

        input(passwordInput) {
            isNotBlank(StringResource(R.string.field_is_blank_error_message))
            minLength(
                PASSWORD_MIN_LENGTH,
                StringDesc.ResourceFormatted(
                    StringResource(R.string.min_length_error_message),
                    PASSWORD_MIN_LENGTH
                )
            )
            validation(
                { str -> str.any { it.isDigit() } },
                StringResource(R.string.must_contain_digit_error_message)
            )
        }

        input(confirmPasswordInput) {
            isNotBlank(StringResource(R.string.field_is_blank_error_message))
            equalsTo(passwordInput, StringResource(R.string.passwords_do_not_match_error_message))
        }

        checked(termsCheckBox, StringResource(R.string.terms_are_accepted_error_message))
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