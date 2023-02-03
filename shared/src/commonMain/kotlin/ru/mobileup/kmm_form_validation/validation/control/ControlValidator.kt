package ru.mobileup.kmm_form_validation.validation.control

import ru.mobileup.kmm_form_validation.control.ValidatableControl

/**
 * Interface for validation of a single control.
 */
interface ControlValidator<ControlT : ValidatableControl<*>> {

    /**
     * A control for validation
     */
    val control: ControlT

    /**
     * Validates a control.
     * @param displayResult specifies if a result will be displayed on UI.
     */
    fun validate(displayResult: Boolean = true): ValidationResult
}