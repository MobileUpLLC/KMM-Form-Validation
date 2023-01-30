package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface Cancelable {
    fun cancel()
}

class FlowWrapper<T : Any>(
    private val flow: Flow<T>
) : Flow<T> by flow {

    fun bind(consumer: (T) -> Unit): Cancelable {
        val job = Job()
        onEach { consumer(it) }.launchIn(CoroutineScope(Dispatchers.Main + job))
        return object : Cancelable {
            override fun cancel() {
                job.cancel()
            }
        }
    }
}