package ru.mobileup.kmm_form_validation.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.kmm_form_validation.util.computed

/**
 * Abstract base class for UI controls that handle state management, validation, and focus behavior.
 * Provides default implementations for visibility, enable/disable state, error handling, and scrolling events.
 *
 * @param T The type of value managed by the control.
 * @param initialValue The initial value of the control.
 * @param coroutineScope The scope used for state computations and event handling.
 */
abstract class BaseControl<T>(
    initialValue: T,
    coroutineScope: CoroutineScope,
) : UIControl<T> {

    override val valueState: MutableStateFlow<T> = MutableStateFlow(initialValue)

    override val visible: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val enabled: MutableStateFlow<Boolean> = MutableStateFlow(true)

    override val error: MutableStateFlow<StringDesc?> = MutableStateFlow(null)

    override val skipInValidation: StateFlow<Boolean> =
        computed(coroutineScope, visible, enabled) { visible, enabled -> !visible || !enabled }

    private val _scrollToItEvent = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val scrollToItEvent: Flow<Unit> = _scrollToItEvent

    override fun requestFocus() {
        _scrollToItEvent.tryEmit(Unit)
    }

    override fun onValueChanged(value: T) {
        valueState.value = value
    }
}
