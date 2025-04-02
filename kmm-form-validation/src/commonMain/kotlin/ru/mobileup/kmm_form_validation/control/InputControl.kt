package ru.mobileup.kmm_form_validation.control

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.TextTransformation
import ru.mobileup.kmm_form_validation.options.VisualTransformation

/**
 * Logical representation of an input field.
 * It provides configuration options and manages the text input state.
 */
class InputControl(
    coroutineScope: CoroutineScope,
    initialText: String = "",
    val singleLine: Boolean = true,
    val maxLength: Int = Int.MAX_VALUE,
    val keyboardOptions: KeyboardOptions = KeyboardOptions(),
    val textTransformation: TextTransformation? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None,
) : BaseControl<String>(initialText, coroutineScope) {

    private val _value = MutableStateFlow(correctText(initialText))

    override val value: StateFlow<String> = _value

    private val _moveCursorEvent = MutableSharedFlow<Int>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    /**
     * Emits an event when the cursor needs to be moved to a specific position.
     */
    val moveCursorEvent: Flow<Int> = _moveCursorEvent

    /**
     * Indicates whether the input field currently has focus.
     */
    val hasFocus: MutableStateFlow<Boolean> = MutableStateFlow(false)

    override fun requestFocus() {
        hasFocus.value = true
        super.requestFocus()
    }

    override fun onValueChange(value: String) {
        _value.value = correctText(value)
    }

    /**
     * Updates the focus state of the input field.
     *
     * @param hasFocus `true` if the field is focused, `false` otherwise.
     */
    fun onFocusChange(hasFocus: Boolean) {
        this.hasFocus.value = hasFocus
    }

    /**
     * Moves the cursor to a specified position within the input text.
     *
     * @param position The index where the cursor should be placed.
     */
    fun moveCursor(position: Int) {
        _moveCursorEvent.tryEmit(position)
    }

    /**
     * Moves the cursor to the end of the text.
     */
    fun moveCursorToEnd() {
        moveCursor(value.value.length)
    }

    private fun correctText(text: String): String {
        val transformedText = textTransformation?.transform(text) ?: text
        return transformedText.take(maxLength)
    }
}
