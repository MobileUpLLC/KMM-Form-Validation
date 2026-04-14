package ru.mobileup.kmm_form_validation.control

import kotlinx.coroutines.CoroutineScope

/**
 * Logical representation of a selectable control (e.g., dropdown, picker).
 * It manages the selected value and provides state updates.
 */
class PickerControl<T>(
    coroutineScope: CoroutineScope,
    initialValue: T? = null,
) : BaseControl<T?>(initialValue, coroutineScope) {
}
