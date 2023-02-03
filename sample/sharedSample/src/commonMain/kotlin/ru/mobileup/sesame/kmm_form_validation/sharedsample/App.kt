package ru.mobileup.sesame.kmm_form_validation.sharedsample

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.sesame.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.sesame.kmm_form_validation.sharedsample.ui.RealFormComponent

object Application {
    fun createFormComponent(componentContext: ComponentContext): FormComponent {
        return RealFormComponent(componentContext)
    }
}