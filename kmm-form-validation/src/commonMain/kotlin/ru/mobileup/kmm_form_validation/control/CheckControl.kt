package ru.mobileup.kmm_form_validation.control

import kotlinx.coroutines.CoroutineScope

/**
 * Logical representation of a checkable control (e.g., CheckBox, Switch).
 * It manages the checked state and provides state updates.
 */
class CheckControl(
    coroutineScope: CoroutineScope,
    initialChecked: Boolean = false,
) : BaseControl<Boolean>(initialChecked, coroutineScope)
