package ru.mobileup.kmm_form_validation.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Control that can be validated.
 * @param ValueT type of value managed by a control.
 *
 * @see: [InputControl]
 * @see: [CheckControl]
 */
interface ValidatableControl<ValueT : Any> {

    /**
     * Control value.
     */
    val value: StateFlow<ValueT>

    /**
     * Displayed error.
     */
    val error: MutableStateFlow<StringDesc?>

    /**
     * Is control should be skipped during validation.
     */
    val skipInValidation: StateFlow<Boolean>

    /**
     * Moves focus to a control.
     */
    fun requestFocus()
}