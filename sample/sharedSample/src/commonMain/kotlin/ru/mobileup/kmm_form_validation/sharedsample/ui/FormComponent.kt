package ru.mobileup.kmm_form_validation.sharedsample.ui

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface FormComponent {

    val nameInput: ru.mobileup.kmm_form_validation.control.InputControl

    val emailInput: ru.mobileup.kmm_form_validation.control.InputControl

    val phoneInput: ru.mobileup.kmm_form_validation.control.InputControl

    val passwordInput: ru.mobileup.kmm_form_validation.control.InputControl

    val confirmPasswordInput: ru.mobileup.kmm_form_validation.control.InputControl

    val termsCheckBox: ru.mobileup.kmm_form_validation.control.CheckControl

    val submitButtonState: StateFlow<SubmitButtonState>

    val dropKonfettiEvent: Flow<Unit>

    fun onSubmitClicked()
}