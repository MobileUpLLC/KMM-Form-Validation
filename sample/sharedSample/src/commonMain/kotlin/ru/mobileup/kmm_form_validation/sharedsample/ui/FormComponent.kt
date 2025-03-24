package ru.mobileup.kmm_form_validation.sharedsample.ui

import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.control.InputControl

interface FormComponent {

    val nameInput: InputControl

    val emailInput: InputControl

    val phoneInput: InputControl

    val passwordInput: InputControl

    val confirmPasswordInput: InputControl

    val termsCheckBox: CheckControl

    val submitButtonState: StateFlow<SubmitButtonState>

    val showConfetti: StateFlow<Boolean>

    fun onSubmitClicked()
}