package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.MutableStateFlow

class CMutableStateFlow<T : Any>(private val mutableStateFlow: MutableStateFlow<T>) :
    CStateFlow<T>(mutableStateFlow), MutableStateFlow<T> by mutableStateFlow {
    constructor(initialValue: T) : this(MutableStateFlow(initialValue))
}

fun <T : Any> CMutableStateFlow<Optional<T>>.updateValue(value: T?) {
    this.value = value.asOptional()
}