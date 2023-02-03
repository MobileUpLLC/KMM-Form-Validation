package ru.mobileup.sesame.kmm_form_validation.validation.control

import dev.icerock.moko.resources.desc.StringDesc

/**
 * Represents a result of validation.
 */
sealed class ValidationResult {

    /**
     * An input is valid.
     */
    object Valid : ValidationResult()

    /**
     * Validation was skipped.
     */
    object Skipped : ValidationResult()

    /**
     * An input is invalid.
     */
    data class Invalid(val errorMessage: StringDesc) : ValidationResult()
}