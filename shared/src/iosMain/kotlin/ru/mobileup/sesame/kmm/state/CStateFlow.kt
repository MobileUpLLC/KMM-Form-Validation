package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.StateFlow

actual open class CStateFlow<T : Any> actual constructor(private val stateFlow: StateFlow<T>) : StateFlow<T> {

    override val replayCache: List<T> = stateFlow.replayCache

    override suspend fun collect(collector: FlowCollector<T>): Nothing = stateFlow.collect(collector)

    override val value: T
        get() = stateFlow.value
}