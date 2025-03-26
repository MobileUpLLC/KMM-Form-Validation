package ru.mobileup.kmm_form_validation.validation.form

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.mobileup.kmm_form_validation.util.computed

/**
 * Validates a form dynamically and emits validation result. Validation called whenever a value or skipInValidation
 * of some control is changed.
 */
fun CoroutineScope.dynamicValidationResult(formValidator: FormValidator): StateFlow<FormValidationResult> {
    val result = MutableStateFlow(formValidator.validate(displayResult = false))

    formValidator.validators.forEach { (control, _) ->
        onControlStateChanged(this, control.value, control.skipInValidation) {
            result.value = formValidator.validate(displayResult = false)
        }
    }

    return result
}

/**
 * Emits a state flow indicating whether the form is completely filled.
 * Updates whenever a control's value or `skipInValidation` state changes.
 */
fun CoroutineScope.dynamicIsFilled(formValidator: FormValidator): StateFlow<Boolean> {
    val isFilled = MutableStateFlow(formValidator.isFilled)

    formValidator.validators.forEach { (control, _) ->
        onControlStateChanged(this, control.value, control.skipInValidation) {
            isFilled.value = formValidator.isFilled
        }
    }

    return isFilled
}

/**
 * Emits a state flow indicating whether the form has any validation errors.
 * Updates whenever a control's error state or `skipInValidation` changes.
 */
fun CoroutineScope.dynamicHasError(formValidator: FormValidator): StateFlow<Boolean> {
    val hasError = MutableStateFlow(formValidator.hasError)

    formValidator.validators.forEach { (control, _) ->
        onControlStateChanged(this, control.error, control.skipInValidation) {
            hasError.value = formValidator.hasError
        }
    }

    return hasError
}

private fun <T> onControlStateChanged(
    coroutineScope: CoroutineScope,
    state: StateFlow<T>,
    skipInValidation: StateFlow<Boolean>,
    callback: () -> Unit,
) {
    computed(coroutineScope, state, skipInValidation) { v1, v2 -> v1 to v2 }
        .drop(1)
        .onEach { callback() }
        .launchIn(coroutineScope)
}
