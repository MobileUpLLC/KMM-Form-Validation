package ru.mobileup.sesame.kmm.sharedsample

import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import ru.mobileup.sesame.kmm.sharedsample.ui.FormComponent
import ru.mobileup.sesame.kmm.sharedsample.ui.RealFormComponent

object Application {
    fun getFormComponent(): FormComponent {
        return RealFormComponent(
            DefaultComponentContext(LifecycleRegistry())
        )
    }

}