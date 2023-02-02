package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.StateFlow

open class CStateFlow<T : Any>(private val stateFlow: StateFlow<T>): StateFlow<T> by stateFlow

fun <T : Any> StateFlow<T>.asCStateFlow(): CStateFlow<T> =
    CStateFlow(this)

fun <T : Any> CStateFlow<Optional<T>>.nullableValue(): T? = this.value.get()