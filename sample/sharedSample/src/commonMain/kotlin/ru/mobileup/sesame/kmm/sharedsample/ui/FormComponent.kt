package ru.mobileup.sesame.kmm.sharedsample.ui

import kotlinx.coroutines.flow.Flow
import ru.mobileup.sesame.kmm.form.control.CheckControl
import ru.mobileup.sesame.kmm.form.control.InputControl
import ru.mobileup.sesame.kmm.state.CStateFlow

interface FormComponent {

    val nameInput: InputControl

    val emailInput: InputControl

    val phoneInput: InputControl

    val passwordInput: InputControl

    val confirmPasswordInput: InputControl

    val termsCheckBox: CheckControl

    val submitButtonState: CStateFlow<SubmitButtonState>

    val dropKonfettiEvent: Flow<Unit>

    fun onSubmitClicked()
}