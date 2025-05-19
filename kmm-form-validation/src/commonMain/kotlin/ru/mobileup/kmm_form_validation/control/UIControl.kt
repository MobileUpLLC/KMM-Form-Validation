package ru.mobileup.kmm_form_validation.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a UI control that can be validated and interacted with.
 * Provides properties to control visibility, enable/disable state, and scrolling behavior.
 *
 * @param T The type of value managed by the control.
 * @see: [InputControl]
 * @see: [CheckControl]
 * @see: [PickerControl]
 */
interface UIControl<T> {

    /**
     * A [CoroutineScope] associated with the control.
     */
    val coroutineScope: CoroutineScope

    /**
     * The current value of the control.
     */
    val value: StateFlow<T>

    /**
     * Displayed error.
     */
    val error: MutableStateFlow<StringDesc?>

    /**
     * Indicates whether the control should be skipped during validation.
     */
    val skipInValidation: StateFlow<Boolean>

    /**
     * Controls the visibility of the UI element.
     * When `false`, the element is hidden from the UI.
     */
    val visible: MutableStateFlow<Boolean>

    /**
     * Controls whether the UI element is enabled.
     * When `false`, interactions with the element are disabled.
     */
    val enabled: MutableStateFlow<Boolean>

    /**
     * Emits an event when the control should be scrolled into view.
     * Used to ensure visibility when needed, such as during form navigation.
     */
    val scrollToItEvent: Flow<Unit>

    /**
     * Updates the control's value.
     * This method should be called whenever the value changes on the UI
     * to ensure that the control state remains in sync.
     *
     * @param value The new value to be set.
     */
    fun onValueChange(value: T)

    /**
     * Moves focus to the control, ensuring it is ready for user interaction.
     */
    fun requestFocus()
}
