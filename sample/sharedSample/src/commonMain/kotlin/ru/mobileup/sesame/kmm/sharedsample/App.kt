package ru.mobileup.sesame.kmm.sharedsample

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.sesame.kmm.sharedsample.ui.FormComponent
import ru.mobileup.sesame.kmm.sharedsample.ui.RealFormComponent

object Application {
    fun createFormComponent(componentContext: ComponentContext): FormComponent {
        return RealFormComponent(componentContext)
    }

}