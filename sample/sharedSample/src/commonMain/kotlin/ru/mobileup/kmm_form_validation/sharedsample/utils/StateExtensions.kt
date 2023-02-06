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