package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.StateFlow

expect open class CStateFlow<T : Any>(stateFlow: StateFlow<T>): StateFlow<T>

fun <T : Any> StateFlow<T>.asCStateFlow(): CStateFlow<T> =
    CStateFlow(this)

fun <T : Any> CStateFlow<Optional<T>>.nullableValue(): T? = this.value.get()