package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.MutableStateFlow

expect class CMutableStateFlow<T : Any>(initialValue: T) : MutableStateFlow<T>, CStateFlow<T>

fun <T : Any> CMutableStateFlow<Optional<T>>.updateValue(value: T?) {
    this.value = value.asOptional()
}