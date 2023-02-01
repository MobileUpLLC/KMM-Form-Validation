package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface Cancelable {
    fun cancel()
}

open class FlowWrapper<T : Any>(
    private val flow: Flow<T>
) : Flow<T> by flow {

    fun bind(consumer: (T) -> Unit): Cancelable {
        val job = Job()
        val scope = CoroutineScope(Dispatchers.Main + job)
        onEach { consumer(it) }.launchIn(scope)
        return object : Cancelable {
            override fun cancel() {
                job.cancel()
            }
        }
    }
}

class MutableStateWrapper<T : Any>(
    private val stateFlow: MutableStateFlow<T>
) : FlowWrapper<T>(stateFlow) {

    fun update(value: T){
        stateFlow.value = value
    }
}