package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.ValidatableControl

/**
 * Interface for validating a single control.
 *
 * @param T The type of control to be validated.
 */
interface ControlValidator<T : ValidatableControl<*>> {

    /**
     * The control associated with this validator.
     */
    val control: T

    /**
     * Determines whether the control contains meaningful user input.
     * The exact definition depends on the control type:
     * - For text inputs, this typically means non-blank input.
     * - For checkable controls (e.g., CheckBox, Switch), this refers to being checked.
     * - For pickers, this means a value has been selected.
     */
    val isFilled: Boolean

    /**
     * Validates the control.
     *
     * @param displayResult If `true`, the validation result will be displayed on the UI.
     * @return The validation result.
     */
    fun validate(displayResult: Boolean = true): ValidationResult
}
