package ru.mobileup.kmm_form_validation.control

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Represents a UI control that can be validated and interacted with.
 * Provides properties to control visibility, enable/disable state, and scrolling behavior.
 *
 * @param T The type of value managed by the control.
 */
interface UIControl<T> : ValidatableControl<T> {

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
     * Moves focus to the control, ensuring it is ready for user interaction.
     */
    fun requestFocus()
}
