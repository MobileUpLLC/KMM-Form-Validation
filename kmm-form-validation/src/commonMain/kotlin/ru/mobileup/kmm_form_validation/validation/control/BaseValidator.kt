package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.UIControl

/**
 * Abstract base class for control validators that handle validation logic and error display.
 * Provides a default implementation for validation execution and error handling.
 *
 * @param T The type of value managed by the validated control.
 * @param C The type of control being validated.
 * @param control The control instance associated with this validator.
 *
 * @see InputValidator
 * @see CheckValidator
 * @see PickerValidator
 */
abstract class BaseValidator<T, C : UIControl<T>>(
    override val control: C,
) : ControlValidator<C> {

    override val dependsOn: Set<UIControl<*>> = emptySet()

    override fun validate(
        displayResult: Boolean,
    ): ValidationResult = if (control.skipInValidation.value) {
        ValidationResult.Skipped
    } else {
        performValidation().also { if (displayResult) displayValidationResult(it) }
    }

    /**
     * Executes the validation logic.
     * Must be implemented by subclasses to define specific validation rules.
     *
     * @return The result of the validation.
     */
    protected abstract fun performValidation(): ValidationResult

    /**
     * Displays validation results on the control.
     * By default, it updates the control's error state.
     * Can be overridden to provide custom error display behavior.
     *
     * @param result The validation result to be displayed.
     */
    protected open fun displayValidationResult(result: ValidationResult) {
        control.error.value = when (result) {
            is ValidationResult.Invalid -> result.errorMessage
            else -> null
        }
    }
}
