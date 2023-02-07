package ru.mobileup.kmm_form_validation.validation.form

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.kmm_form_validation.control.ValidatableControl
import ru.mobileup.kmm_form_validation.validation.control.ControlValidator
import ru.mobileup.kmm_form_validation.validation.control.InputValidator
import ru.mobileup.kmm_form_validation.validation.control.ValidationResult
/**
 * High level feature for [FormValidator].
 */
interface FormValidationFeature {

    fun install(coroutineScope: CoroutineScope, formValidator: FormValidator)
}

/**
 * Validates a control whenever it loses a focus.
 */
object ValidateOnFocusLost : FormValidationFeature {

    override fun install(coroutineScope: CoroutineScope, formValidator: FormValidator) {
        formValidator.validators.forEach { (_, validator) ->
            if (validator is InputValidator) {
                validateOnFocusLost(coroutineScope, validator)
            }
        }
    }

    private fun validateOnFocusLost(
        coroutineScope: CoroutineScope,
        inputValidator: InputValidator
    ) {
        val inputControl = inputValidator.control

        inputControl.hasFocus
            .drop(1)
            .filter { !it }
            .onEach {
                inputValidator.validate()
            }
            .launchIn(coroutineScope)
    }
}

/**
 * Validates control again whenever its value is changed and it already displays an error.
 */
object RevalidateOnValueChanged : FormValidationFeature {

    override fun install(coroutineScope: CoroutineScope, formValidator: FormValidator) {
        formValidator.validators.forEach { (_, validator) ->
            revalidateOnValueChanged(coroutineScope, validator)
        }
    }

    private fun revalidateOnValueChanged(
        coroutineScope: CoroutineScope,
        validator: ControlValidator<*>
    ) {
        val control = validator.control
        control.value
            .drop(1)
            .onEach {
                if (control.error.value != null) {
                    validator.validate()
                }
            }
            .launchIn(coroutineScope)
    }
}

/**
 * Hides an error on a control whenever some value is entered to it.
 */
object HideErrorOnValueChanged : FormValidationFeature {

    override fun install(coroutineScope: CoroutineScope, formValidator: FormValidator) {
        formValidator.validators.forEach { (control, _) ->
            hideErrorOnValueChanged(coroutineScope, control)
        }
    }

    private fun hideErrorOnValueChanged(
        coroutineScope: CoroutineScope,
        control: ValidatableControl<*>
    ) {
        control.value
            .drop(1)
            .onEach {
                control.error.value = null
            }
            .launchIn(coroutineScope)
    }
}

/**
 * Sets focus on a first invalid control after form validation has been processed.
 */
object SetFocusOnFirstInvalidControlAfterValidation : FormValidationFeature {

    override fun install(coroutineScope: CoroutineScope, formValidator: FormValidator) {
        formValidator.validatedEventFlow
            .onEach {
                if (it.displayResult) {
                    focusOnFirstInvalidControl(it.result)
                }
            }
            .launchIn(coroutineScope)
    }

    private fun focusOnFirstInvalidControl(validationResult: FormValidationResult) {
        val firstInvalidControl = validationResult.controlResults.entries
            .firstOrNull { it.value is ValidationResult.Invalid }?.key

        firstInvalidControl?.requestFocus()
    }
}