package ru.mobileup.kmm_form_validation.control

import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import ru.mobileup.kmm_form_validation.util.computed

/**
 * Logical representation of a selectable control (e.g., dropdown, picker).
 * It manages the selected value and provides state updates.
 *
 * @param displayMapper A function that maps the selected value to a user-friendly string representation.
 */
class PickerControl<T>(
    coroutineScope: CoroutineScope,
    initialValue: T? = null,
    displayMapper: (T?) -> StringDesc?,
) : BaseControl<T?>(initialValue, coroutineScope) {

    /**
     * A computed state representing the displayable text of the selected value.
     * It applies the provided [displayMapper] function to transform the selected value into a user-friendly string.
     */
    val displayValue: StateFlow<StringDesc?> = computed(coroutineScope, value, displayMapper)
}
