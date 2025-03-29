package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.PickerControl

class PickerValidatorBuilder<T>(
    private val control: PickerControl<T>,
    val required: Boolean = true,
) {

    private val validations = mutableListOf<(T?) -> ValidationResult>()

    /**
     * Adds an arbitrary validation. Validations are processed sequentially until first error.
     */
    fun validation(validation: (T?) -> ValidationResult) {
        validations.add(validation)
    }

    fun build(): PickerValidator<T> {
        return PickerValidator(control, required, validations)
    }
}

/**
 * Adds a validation rule with a custom error message.
 *
 * @param errorMessage The error message to be displayed if validation fails.
 * @param isValid A function that checks whether the given value is valid.
 */
fun <T> PickerValidatorBuilder<T>.validation(
    errorMessage: StringDesc,
    isValid: (T?) -> Boolean,
) = validation {
    if (isValid(it)) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(errorMessage)
    }
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
