package ru.mobileup.kmm_form_validation.sharedsample.ui

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.options.KeyboardCapitalization
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType

@OptIn(DelicateCoroutinesApi::class)
class FakeFormComponent : FormComponent, ComponentContext by DefaultComponentContext(
    LifecycleRegistry()
) {

    private val fakeScope = GlobalScope

    override val nameInput = InputControl(
        fakeScope,
        initialText = "Some name",
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
    )

    override val emailInput = InputControl(
        fakeScope,
        initialText = "some@email.com",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )

    override val phoneInput = InputControl(
        fakeScope,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
    )

    override val passwordInput = InputControl(
        fakeScope,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

    override val confirmPasswordInput = InputControl(
        fakeScope,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

    override val termsCheckBox = CheckControl(fakeScope)

    override val submitButtonState = MutableStateFlow(SubmitButtonState.Valid)

    override val showConfetti = MutableStateFlow(false)

    override fun onSubmitClicked() = Unit
}