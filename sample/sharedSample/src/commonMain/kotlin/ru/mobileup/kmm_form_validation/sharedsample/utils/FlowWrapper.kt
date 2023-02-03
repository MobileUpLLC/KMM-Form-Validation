package ru.mobileup.kmm_form_validation.sharedsample.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface Cancelable {
    fun cancel()
}

open class FlowWrapper<T>(
    private val flow: Flow<T>
) {

    fun collect(consumer: (T) -> Unit): Cancelable {
        val scope = CoroutineScope(Dispatchers.Main.immediate)

        flow
            .onEach { consumer(it) }
            .launchIn(scope)

        return object : Cancelable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}

class MutableStateFlowWrapper<T>(
    private val stateFlow: MutableStateFlow<T>
) : FlowWrapper<T>(stateFlow) {

    fun update(value: T) {
        stateFlow.value = value
    }
}