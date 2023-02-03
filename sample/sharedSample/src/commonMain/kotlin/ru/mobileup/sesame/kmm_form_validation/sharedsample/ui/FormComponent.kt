package ru.mobileup.sesame.kmm_form_validation.sharedsample.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.sesame.kmm_form_validation.form.control.CheckControl
import ru.mobileup.sesame.kmm_form_validation.form.control.InputControl

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