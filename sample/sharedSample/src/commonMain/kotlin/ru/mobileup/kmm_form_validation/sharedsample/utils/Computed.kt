package ru.mobileup.kmm_form_validation.sharedsample.utils

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.launch

internal fun <T, R> ComponentContext.computed(
    flow: StateFlow<T>,
    transform: (T) -> R
): StateFlow<R> {
    val initialValue = flow.value
    val resultFlow = MutableStateFlow(transform(initialValue))
    componentScope.launch {
        flow.dropWhile {
            it == initialValue
        }.collect {
            resultFlow.value = transform(it)
        }
    }
    return resultFlow
}

@Suppress("UNCHECKED_CAST")
fun <T1, T2, R> ComponentContext.computed(
    flow1: StateFlow<T1>,
    flow2: StateFlow<T2>,
    transform: (T1, T2) -> R
): StateFlow<R> {
    return computedImpl(flow1, flow2) { args: List<*> ->
        transform(
            args[0] as T1,
            args[1] as T2
        )
    }
}

private inline fun <T, R> ComponentContext.computedImpl(
    vararg flows: StateFlow<T>,
    crossinline transform: (List<T>) -> R
): StateFlow<R> {
    val initialValues: List<T> = flows.map { it.value }
    val elementsFlow: MutableStateFlow<List<T>> = MutableStateFlow(initialValues)
    val resultFlow: MutableStateFlow<R> = MutableStateFlow(transform(initialValues))

    flows.forEachIndexed { index, flow ->
        componentScope.launch {
            flow
                .dropWhile { it == initialValues[index] }
                .collect {
                    elementsFlow.value = elementsFlow.value
                        .toMutableList()
                        .apply { this[index] = it }
                }
        }
    }

    componentScope.launch {
        elementsFlow
            .dropWhile { it == initialValues }
            .collect { resultFlow.value = transform(it) }
    }

    return resultFlow
}
