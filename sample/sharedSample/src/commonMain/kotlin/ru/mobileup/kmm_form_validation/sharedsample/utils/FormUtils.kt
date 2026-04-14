package ru.mobileup.kmm_form_validation.sharedsample.utils

import com.arkivanov.decompose.ComponentContext
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.control.PickerControl
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.TextTransformation
import ru.mobileup.kmm_form_validation.validation.form.FormValidator
import ru.mobileup.kmm_form_validation.validation.form.FormValidatorBuilder
import ru.mobileup.kmm_form_validation.validation.form.formValidator

fun ComponentContext.InputControl(
    initialText: String = "",
    singleLine: Boolean = true,
    maxLength: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    textTransformation: TextTransformation? = null,
): InputControl = InputControl(
    componentScope,
    initialText,
    singleLine,
    maxLength,
    keyboardOptions,
    textTransformation
)

fun ComponentContext.CheckControl(
    initialChecked: Boolean = false,
): CheckControl = CheckControl(componentScope, initialChecked)

fun <T> ComponentContext.PickerControl(
    initialValue: T? = null,
): PickerControl<T> = PickerControl(componentScope, initialValue)

fun ComponentContext.formValidator(
    buildBlock: FormValidatorBuilder.() -> Unit,
): FormValidator = componentScope.formValidator(buildBlock)
