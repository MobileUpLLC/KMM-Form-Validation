package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.SharedFlow

open class CSharedFlow<T : Any>(private val sharedFlow: SharedFlow<T>) : SharedFlow<T> by sharedFlow

fun <T : Any> SharedFlow<T>.asCSharedFlow(): CSharedFlow<T> =
    CSharedFlow(this)

fun <T : Any> CSharedFlow<Optional<T>>.nullableReplayCache(): List<T?> = this.replayCache.map { it.get() }