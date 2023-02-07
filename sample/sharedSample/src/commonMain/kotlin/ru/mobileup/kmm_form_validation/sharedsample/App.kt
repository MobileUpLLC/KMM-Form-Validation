package ru.mobileup.kmm_form_validation.sharedsample

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.RealFormComponent

object Core {
    fun createFormComponent(componentContext: ComponentContext): FormComponent {
        return RealFormComponent(componentContext)
    }
}