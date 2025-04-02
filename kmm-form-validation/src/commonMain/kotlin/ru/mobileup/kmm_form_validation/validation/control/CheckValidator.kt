package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder
import ru.mobileup.kmm_form_validation.validation.form.checked

/**
 * Validator for [CheckControl] that ensures a checkable control (e.g., CheckBox, Switch) meets validation requirements.
 * Use [FormValidatorBuilder.checked] to create it with a handy DSL.
 *
 * @param validation A function that defines the validation logic based on the checked state.
 * @param showError A callback that is called to show one-time error such as a toast.
 * For permanent errors use [CheckControl.error] state.
 */
class CheckValidator(
    override val control: CheckControl,
    private val validation: (Boolean) -> ValidationResult,
    private val showError: ((StringDesc) -> Unit)? = null,
) : BaseValidator<Boolean, CheckControl>(control) {

    override fun performValidation(): ValidationResult = validation(control.valueState.value)

    override fun displayValidationResult(result: ValidationResult) {
        super.displayValidationResult(result)
        if (result is ValidationResult.Invalid) showError?.invoke(result.errorMessage)
    }

    /**
     * Indicates whether the checkable control is in a "checked" state.
     */
    override val isFilled: Boolean
        get() = control.valueState.value
}
