package ru.mobileup.kmm_form_validation.validation.form

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import kotlinx.coroutines.CoroutineScope
import ru.mobileup.kmm_form_validation.control.CheckControl
import ru.mobileup.kmm_form_validation.control.InputControl
import ru.mobileup.kmm_form_validation.control.PickerControl
import ru.mobileup.kmm_form_validation.control.UIControl
import ru.mobileup.kmm_form_validation.validation.control.CheckValidator
import ru.mobileup.kmm_form_validation.validation.control.ControlValidator
import ru.mobileup.kmm_form_validation.validation.control.InputValidatorBuilder
import ru.mobileup.kmm_form_validation.validation.control.PickerValidatorBuilder
import ru.mobileup.kmm_form_validation.validation.control.ValidationResult

class FormValidatorBuilder {

    private val validators = mutableMapOf<UIControl<*>, ControlValidator<*>>()

    private val dependencies = mutableMapOf<ControlValidator<*>, Set<UIControl<*>>>()

    /**
     * Allows to add additional features to form validation.
     * @see [FormValidationFeature].
     */
    var features = listOf<FormValidationFeature>()

    /**
     * Adds arbitrary [ControlValidator].
     */
    fun validator(validator: ControlValidator<*>) {
        val control = validator.control
        if (validators.containsKey(control)) {
            throw IllegalArgumentException("Validator for $control is already added.")
        }
        validators[control] = validator
    }

    fun dependency(validator: ControlValidator<*>, dependsOn: Set<UIControl<*>>) {
        if (dependencies.containsKey(validator)) {
            throw IllegalArgumentException("Dependency for $validator is already added.")
        }
        dependencies[validator] = dependsOn
    }

    /**
     * Adds a validator for [CheckControl].
     * @param validation implements validation logic.
     * @param showError a callback that is called to show one-time error such as a toast. For permanent errors use [CheckControl.error] state.
     */
    fun check(
        checkControl: CheckControl,
        showError: ((StringDesc) -> Unit)? = null,
        validation: (Boolean) -> ValidationResult,
    ) {
        CheckValidator(checkControl, validation, showError)
            .run(::validator)
    }

    /**
     * Adds a validator for [InputControl]. Use [buildBlock] to configure validation for a given control.
     * @param required specifies if blank input is considered valid.
     */
    fun input(
        inputControl: InputControl,
        required: Boolean = true,
        dependsOn: Set<UIControl<*>> = emptySet(),
        buildBlock: InputValidatorBuilder.() -> Unit,
    ) {
        InputValidatorBuilder(inputControl, required)
            .apply(buildBlock)
            .build()
            .also { dependency(it, dependsOn) }
            .run(::validator)
    }

    /**
     * Adds a validator for [PickerControl]. Use [buildBlock] to configure validation for a given control.
     * @param required Specifies whether a value must be selected for validation to pass.
     */
    fun <T> picker(
        pickerControl: PickerControl<T>,
        required: Boolean = true,
        buildBlock: PickerValidatorBuilder<T>.() -> Unit,
    ) {
        PickerValidatorBuilder(pickerControl, required)
            .apply(buildBlock)
            .build()
            .run(::validator)
    }

    fun build(
        coroutineScope: CoroutineScope,
    ): FormValidator = FormValidator(validators, dependencies, coroutineScope).apply {
        features.forEach { feature ->
            feature.install(coroutineScope, this)
        }
    }
}

/**
 * Creates [FormValidator]. Use [buildBlock] to configure validation.
 */
fun CoroutineScope.formValidator(
    buildBlock: FormValidatorBuilder.() -> Unit,
): FormValidator = FormValidatorBuilder().apply(buildBlock).build(this)

/**
 * Adds a validator that checks that [checkControl] is checked.
 */
fun FormValidatorBuilder.checked(
    checkControl: CheckControl,
    errorMessage: StringDesc,
    showError: ((StringDesc) -> Unit)? = null,
) = check(checkControl, showError) {
    if (it) ValidationResult.Valid else ValidationResult.Invalid(errorMessage)
}

/**
 * Adds a validator that checks that [checkControl] is checked.
 */
fun FormValidatorBuilder.checked(
    checkControl: CheckControl,
    errorMessageRes: StringResource,
    showError: ((StringDesc) -> Unit)? = null,
) = checked(checkControl, StringDesc.Resource(errorMessageRes), showError)
