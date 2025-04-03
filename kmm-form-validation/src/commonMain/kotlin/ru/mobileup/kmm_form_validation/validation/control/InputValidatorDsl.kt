package ru.mobileup.kmm_form_validation.validation.control

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.mobileup.kmm_form_validation.control.InputControl

/**
 * A builder for creating an [InputValidator] with various validation rules.
 *
 * To add a custom validation rule, use extension functions on [InputValidatorBuilder].
 *
 * @param inputControl The input control to be validated.
 * @param required Whether the input is required.
 *
 * @see isNotBlank
 * @see regex
 * @see minLength
 * @see equalsTo
 */
class InputValidatorBuilder(
    inputControl: InputControl,
    required: Boolean,
) : BaseValidatorBuilder<String, InputControl, InputValidator>(inputControl, required) {

    override fun build(): InputValidator = InputValidator(control, dependsOn, required, validations)
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
) {
    dependsOn(inputControl)
    validation(errorMessage) { it == inputControl.value.value }
}

/**
 * Adds a validation that checks that an input equals to an input of another input control.
 */
fun InputValidatorBuilder.equalsTo(
    inputControl: InputControl,
    errorMessageRes: StringResource,
) = equalsTo(inputControl, StringDesc.Resource(errorMessageRes))
