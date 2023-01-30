package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

actual class CMutableStateFlow<T : Any>(
    private val flow: MutableStateFlow<T>,
) : CStateFlow<T>(flow), MutableStateFlow<T> {

    actual constructor(initialValue: T) : this(MutableStateFlow(initialValue))

    override var value: T
        get() = super.value
        set(value) {
            flow.value = value
        }

    override val subscriptionCount: StateFlow<Int>
        get() = flow.subscriptionCount

    override suspend fun emit(value: T) = flow.emit(value)

    @ExperimentalCoroutinesApi
    override fun resetReplayCache() = flow.resetReplayCache()

    override fun tryEmit(value: T): Boolean = flow.tryEmit(value)

    override fun compareAndSet(expect: T, update: T): Boolean = flow.compareAndSet(expect, update)
}