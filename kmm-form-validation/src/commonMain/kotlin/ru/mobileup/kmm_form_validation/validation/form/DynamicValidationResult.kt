package ru.mobileup.kmm_form_validation.validation.form

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import ru.mobileup.kmm_form_validation.control.ValidatableControl
import ru.mobileup.kmm_form_validation.util.computed

/**
 * Validates a form dynamically and emits validation result. Validation called whenever a value or skipInValidation
 * of some control is changed.
 */
fun CoroutineScope.dynamicValidationResult(formValidator: FormValidator): StateFlow<FormValidationResult> {
    val result = MutableStateFlow(formValidator.validate(displayResult = false))
    formValidator.validators.forEach { (control, _) ->
        callWhenControlEdited(this, control) {
            result.value = formValidator.validate(displayResult = false)
        }
    }
    return result
}

private fun callWhenControlEdited(
    coroutineScope: CoroutineScope,
    control: ValidatableControl<*>,
    callback: () -> Unit
) {
    computed(coroutineScope, control.value, control.skipInValidation) { v1, v2 -> v1 to v2 }
        .drop(1)
        .onEach { callback() }
        .launchIn(coroutineScope)
}