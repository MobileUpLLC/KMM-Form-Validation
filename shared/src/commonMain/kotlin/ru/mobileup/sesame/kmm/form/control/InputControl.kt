package ru.mobileup.sesame.kmm.form.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.mobileup.sesame.kmm.form.options.KeyboardOptions
import ru.mobileup.sesame.kmm.form.util.computed
import ru.mobileup.sesame.kmm.state.*

/**
 * Logical representation of an input field. It allows to configure an input field and manage its state from ViewModel.
 */
class InputControl(
    coroutineScope: CoroutineScope,
    initialText: String = "",
    val singleLine: Boolean = true,
    val maxLength: Int = Int.MAX_VALUE,
    val keyboardOptions: KeyboardOptions,
    val textTransformation: TextTransformation? = null,
    val visualTransformation: VisualTransformation = VisualTransformation.None
) : ValidatableControl<String> {

    constructor(coroutineScope: CoroutineScope) : this(
        coroutineScope = coroutineScope,
        initialText = "",
        singleLine = true,
        maxLength = Int.MAX_VALUE,
        keyboardOptions = KeyboardOptions(),
        textTransformation = null,
        visualTransformation = VisualTransformation.None
    )

    private val _text = CMutableStateFlow(correctText(initialText))

    /**
     * Current text.
     */
    val text: CStateFlow<String>
        get() = _text

    /**
     * Is control visible.
     */
    val visible: CMutableStateFlow<Boolean> = CMutableStateFlow(true)

    /**
     * Is control enabled.
     */
    val enabled: CMutableStateFlow<Boolean> = CMutableStateFlow(true)

    /**
     * Is control has focus.
     */
    val hasFocus: CMutableStateFlow<Boolean> = CMutableStateFlow(false)

    /**
     * Displayed error.
     */
    override val error: CMutableStateFlow<Optional<StringDesc>> = CMutableStateFlow(Optional())

    override val value: CStateFlow<String> = _text

    override val skipInValidation =
        computed(
            coroutineScope,
            visible,
            enabled
        ) { visible, enabled -> !visible || !enabled }.asCStateFlow()

    private val mutableScrollToItEventFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val scrollToItEvent get() = mutableScrollToItEventFlow.asSharedFlow().asCSharedFlow()

    override fun requestFocus() {
        this.hasFocus.value = true
        mutableScrollToItEventFlow.tryEmit(Unit)
    }

    fun setText(text: String) {
        _text.value = correctText(text)
    }

    /**
     * Should be called when text is changed on a view side.
     */
    fun onTextChanged(text: String) {
        _text.value = correctText(text)
    }

    fun onFocusChanged(hasFocus: Boolean) {
        this.hasFocus.value = hasFocus
    }

    private fun correctText(text: String): String {
        val transformedText = textTransformation?.transform(text) ?: text
        return transformedText.take(maxLength)
    }
}
