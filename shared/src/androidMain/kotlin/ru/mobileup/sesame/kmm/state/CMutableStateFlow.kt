package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.MutableStateFlow

actual class CMutableStateFlow<T : Any>(mutableStateFlow: MutableStateFlow<T>) :
    CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    actual constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}