package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.PickerControl

/**
 * A builder for creating a [PickerValidator] with various validation rules.
 *
 * To add a custom validation rule, use extension functions on [PickerValidatorBuilder].
 *
 * @param control The picker control to be validated.
 * @param required Whether the selection is required.
 *
 * @see isPicked
 */
class PickerValidatorBuilder<T>(
    control: PickerControl<T>,
    required: Boolean,
) : BaseValidatorBuilder<T?, PickerControl<T>, PickerValidator<T>>(control, required) {

    override fun build(): PickerValidator<T> =
        PickerValidator(control, dependsOn, required, validations)
}

/**
 * Adds a validation rule that ensures a value is selected.
 *
 * @param errorMessage The error message to be displayed if no value is selected.
 */
fun <T> PickerValidatorBuilder<T>.isPicked(errorMessage: StringDesc) =
    validation(errorMessage) { it != null }

/**
 * Adds a validation rule that ensures a value is selected.
 *
 * @param errorMessageRes The resource ID of the error message.
 */
fun <T> PickerValidatorBuilder<T>.isPicked(errorMessageRes: StringResource) =
    isPicked(StringDesc.Resource(errorMessageRes))
