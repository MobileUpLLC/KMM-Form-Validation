package ru.mobileup.kmm_form_validation.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Control that can be validated.
 * @param T type of value managed by a control.
 *
 * @see: [InputControl]
 * @see: [CheckControl]
 * @see: [PickerControl]
 */
interface ValidatableControl<T> {

    /**
     * The current value of the control.
     */
    val valueState: StateFlow<T>

    /**
     * Displayed error.
     */
    val error: MutableStateFlow<StringDesc?>

    /**
     * Indicates whether the control should be skipped during validation.
     */
    val skipInValidation: StateFlow<Boolean>

    /**
     * Updates the control's value.
     * This method should be called whenever the value changes on the UI
     * to ensure that the control state remains in sync.
     *
     * @param value The new value to be set.
     */
    fun onValueChanged(value: T)
}
