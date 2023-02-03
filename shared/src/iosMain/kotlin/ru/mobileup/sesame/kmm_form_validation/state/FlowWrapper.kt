package ru.mobileup.sesame.kmm_form_validation.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

interface Cancelable {
    fun cancel()
}

open class FlowWrapper<T>(
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

class MutableFlowWrapper<T>(
    private val mutableFlow: MutableSharedFlow<T>
) : FlowWrapper<T>(mutableFlow) {

    fun update(value: T){
        mutableFlow.tryEmit(value)
    }
}