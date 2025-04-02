package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.InputControl

class InputValidatorBuilder(
    private val inputControl: InputControl,
    private val required: Boolean,
) {

    private val validations = mutableListOf<(String) -> ValidationResult>()

    /**
     * Adds an arbitrary validation. Validations are processed sequentially until first error.
     */
    fun validation(validation: (String) -> ValidationResult) {
        validations.add(validation)
    }

    fun build(): InputValidator {
        return InputValidator(inputControl, required, validations)
    }
}

/**
 * Adds an arbitrary validation. Validations are processed sequentially until first error.
 */
fun InputValidatorBuilder.validation(errorMessage: StringDesc, isValid: (String) -> Boolean) {
    validation {
        if (isValid(it)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(errorMessage)
        }
    }
}

/**
 * Adds an arbitrary validation. Validations are processed sequentially until first error.
 */
fun InputValidatorBuilder.validation(
    errorMessageRes: StringResource,
    isValid: (String) -> Boolean,
) {
    validation(StringDesc.Resource(errorMessageRes), isValid)
}

/**
 * Adds an arbitrary validation. Validations are processed sequentially until first error.
 */
fun InputValidatorBuilder.validation(
    errorMessage: () -> StringDesc,
    isValid: (String) -> Boolean,
) {
    validation {
        if (isValid(it)) {
            ValidationResult.Valid
        } else {
            ValidationResult.Invalid(errorMessage.invoke())
        }
    }
}

/**
 * Adds a validation that checks that an input is not blank.
 */
fun InputValidatorBuilder.isNotBlank(errorMessage: StringDesc) =
    validation(errorMessage, String::isNotBlank)

/**
 * Adds a validation that checks that an input is not blank.
 */
fun InputValidatorBuilder.isNotBlank(errorMessageRes: StringResource) =
    isNotBlank(StringDesc.Resource(errorMessageRes))

/**
 * Adds a validation that checks that an input matches [regex].
 */
fun InputValidatorBuilder.regex(regex: Regex, errorMessage: StringDesc) =
    validation(errorMessage, regex::matches)

/**
 * Adds a validation that checks that an input matches [regex].
 */
fun InputValidatorBuilder.regex(regex: Regex, errorMessageRes: StringResource) =
    regex(regex, StringDesc.Resource(errorMessageRes))

/**
 * Adds a validation that checks that an input has at least given number of symbols.
 */
fun InputValidatorBuilder.minLength(length: Int, errorMessage: StringDesc) =
    validation(errorMessage) { it.length >= length }

/**
 * Adds a validation that checks that an input has at least given number of symbols.
 */
fun InputValidatorBuilder.minLength(length: Int, errorMessageRes: StringResource) =
    minLength(length, StringDesc.Resource(errorMessageRes))

/**
 * Adds a validation that checks that an input equals to an input of another input control.
 */
fun InputValidatorBuilder.equalsTo(
    inputControl: InputControl,
    errorMessage: StringDesc,
) = validation(errorMessage) { it == inputControl.value.value }

/**
 * Adds a validation that checks that an input equals to an input of another input control.
 */
fun InputValidatorBuilder.equalsTo(
    inputControl: InputControl,
    errorMessageRes: StringResource,
) = equalsTo(inputControl, StringDesc.Resource(errorMessageRes))
