package ru.mobileup.sesame.kmm.android_sample.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.sesame.kmm.form.control.CheckControl
import ru.mobileup.sesame.kmm.form.control.InputControl

interface FormComponent {

    val nameInput: InputControl

    val emailInput: InputControl

    val phoneInput: InputControl

    val passwordInput: InputControl

    val confirmPasswordInput: InputControl

    val termsCheckBox: CheckControl

    val submitButtonState: StateFlow<SubmitButtonState>

    val dropKonfettiEvent: Flow<Unit>

    fun onSubmitClicked()
}