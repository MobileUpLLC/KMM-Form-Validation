package ru.mobileup.sesame.kmm.form.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.mobileup.sesame.kmm.form.util.computed
import ru.mobileup.sesame.kmm.state.CMutableStateFlow
import ru.mobileup.sesame.kmm.state.Optional
import ru.mobileup.sesame.kmm.state.asCSharedFlow
import ru.mobileup.sesame.kmm.state.asCStateFlow

/**
 * Logical representation of a control with checkable state (CheckBox, Switch, etc). It allows to manage checked state from ViewModel.
 */
class CheckControl(
    coroutineScope: CoroutineScope,
    initialChecked: Boolean = false
) : ValidatableControl<Boolean> {

    /**
     * Is control checked.
     */
    val checked: CMutableStateFlow<Boolean> = CMutableStateFlow(initialChecked)

    /**
     * Is control visible.
     */
    val visible: CMutableStateFlow<Boolean> = CMutableStateFlow(true)

    /**
     * Is control enabled.
     */
    val enabled: CMutableStateFlow<Boolean> = CMutableStateFlow(true)

    /**
     * Displayed error.
     */
    override val error: CMutableStateFlow<Optional<StringDesc>> = CMutableStateFlow(Optional())

    override val value = checked

    override val skipInValidation =
        computed(coroutineScope, visible, enabled) { visible, enabled -> !visible || !enabled }.asCStateFlow()

    //TODO: fix for ios
    private val mutableScrollToItEventFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val scrollToItEvent get() = mutableScrollToItEventFlow.asSharedFlow().asCSharedFlow()

    override fun requestFocus() {
        mutableScrollToItEventFlow.tryEmit(Unit)
    }

    /**
     * Called automatically when checked is changed on a view side.
     */
    fun onCheckedChanged(checked: Boolean) {
        this.checked.value = checked
    }
}
