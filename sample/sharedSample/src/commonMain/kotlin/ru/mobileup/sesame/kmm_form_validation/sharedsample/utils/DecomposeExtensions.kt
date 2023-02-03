package ru.mobileup.sesame.kmm_form_validation.sharedsample.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import ru.mobileup.sesame.kmm_form_validation.control.CheckControl
import ru.mobileup.sesame.kmm_form_validation.control.InputControl
import ru.mobileup.sesame.kmm_form_validation.control.TextTransformation
import ru.mobileup.sesame.kmm_form_validation.control.VisualTransformation
import ru.mobileup.sesame.kmm_form_validation.options.KeyboardOptions

fun LifecycleOwner.componentCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    lifecycle.doOnDestroy {
        scope.cancel()
    }
    return scope
}

fun ComponentContext.InputControl(
    initialText: String = "",
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions,
    textTransformation: TextTransformation? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) = InputControl(
    componentCoroutineScope(),
    initialText,
    singleLine,
    maxLength,
    keyboardOptions,
    textTransformation,
    visualTransformation
)

fun ComponentContext.CheckControl(initialChecked: Boolean = false) =
    CheckControl(componentCoroutineScope(), initialChecked)