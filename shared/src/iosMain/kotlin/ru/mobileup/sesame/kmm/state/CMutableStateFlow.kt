package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.MutableStateFlow

actual class CMutableStateFlow<T : Any>(
    private val stateFlow: MutableStateFlow<T>,
) : CStateFlow<T>(stateFlow), MutableStateFlow<T> by stateFlow {

    actual constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}
