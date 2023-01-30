package ru.mobileup.sesame.kmm.form.control

import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.sesame.kmm.state.CMutableStateFlow
import ru.mobileup.sesame.kmm.state.CStateFlow
import ru.mobileup.sesame.kmm.state.Optional

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
    val value: CStateFlow<ValueT>

    /**
     * Displayed error.
     */
    val error: CMutableStateFlow<Optional<StringDesc>>

    /**
     * Is control should be skipped during validation.
     */
    val skipInValidation: CStateFlow<Boolean>

    /**
     * Moves focus to a control.
     */
    fun requestFocus()
}