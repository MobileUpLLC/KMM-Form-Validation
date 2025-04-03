package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.UIControl

/**
 * A base class for building control validators.
 *
 * This class provides common functionality for defining validation rules and dependencies
 * for form controls. It allows adding validation functions that are executed sequentially
 * and defining dependencies on other UI controls.
 *
 * @param T The type of the value being validated.
 * @param C The type of the UI control associated with this validator.
 * @param V The type of the control validator being built.
 * @property control The UI control associated with this validator.
 * @property required Indicates whether this control is required.
 *
 * @see InputValidatorBuilder
 * @see PickerValidatorBuilder
 */
abstract class BaseValidatorBuilder<T, C : UIControl<T>, V : ControlValidator<*>>(
    protected val control: C,
    protected val required: Boolean,
) {

    /** A list of validation functions executed sequentially until the first error occurs. */
    protected val validations = mutableListOf<(T) -> ValidationResult>()

    /** A set of controls that this validator depends on. */
    protected val dependsOn = mutableSetOf<UIControl<*>>()

    /**
     * Adds an arbitrary validation. Validations are processed sequentially until first error.
     */
    fun validation(validation: (T) -> ValidationResult) {
        validations.add(validation)
    }

    /**
     * Adds a dependency on another UI control.
     */
    fun dependsOn(control: UIControl<*>) {
        if (this.control != control) dependsOn.add(control)
    }

    abstract fun build(): V
}

/**
 * Adds a validation rule with a custom error message.
 *
 * @param errorMessage The error message to be displayed if validation fails.
 * @param isValid A function that checks whether the given value is valid.
 */
fun <T, C : UIControl<T>, V : ControlValidator<*>> BaseValidatorBuilder<T, C, V>.validation(
    errorMessage: StringDesc,
    isValid: (T) -> Boolean,
) = validation {
    if (isValid(it)) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(errorMessage)
    }
}

/**
 * Adds a validation rule with a custom error message.
 *
 * @param errorMessageRes The error message resource to be displayed if validation fails.
 * @param isValid A function that checks whether the given value is valid.
 */
fun <T, C : UIControl<T>, V : ControlValidator<*>> BaseValidatorBuilder<T, C, V>.validation(
    errorMessageRes: StringResource,
    isValid: (T) -> Boolean,
) = validation(StringDesc.Resource(errorMessageRes), isValid)

/**
 * Adds a validation rule with a custom error message.
 *
 * @param errorMessage The error message to be displayed if validation fails.
 * @param isValid A function that checks whether the given value is valid.
 */
fun <T, C : UIControl<T>, V : ControlValidator<*>> BaseValidatorBuilder<T, C, V>.validation(
    errorMessage: () -> StringDesc,
    isValid: (T) -> Boolean,
) = validation {
    if (isValid(it)) {
        ValidationResult.Valid
    } else {
        ValidationResult.Invalid(errorMessage())
    }
}
