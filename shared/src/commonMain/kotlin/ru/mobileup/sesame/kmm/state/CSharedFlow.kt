package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.SharedFlow

expect open class CSharedFlow<T : Any>(sharedFlow: SharedFlow<T>) : SharedFlow<T>

fun <T : Any> SharedFlow<T>.asCSharedFlow(): CSharedFlow<T> =
    CSharedFlow(this)

fun <T : Any> CSharedFlow<Optional<T>>.nullableReplayCache(): List<T?> = this.replayCache.map { it.get() }