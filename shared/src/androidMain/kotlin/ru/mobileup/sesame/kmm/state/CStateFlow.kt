package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.StateFlow

actual open class CStateFlow<T : Any> actual constructor(stateFlow: StateFlow<T>) : StateFlow<T> by stateFlow