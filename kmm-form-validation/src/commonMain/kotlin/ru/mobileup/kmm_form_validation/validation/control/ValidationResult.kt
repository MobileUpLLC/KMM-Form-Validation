package ru.mobileup.kmm_form_validation.validation.control

/**
 * A marker interface for validation errors.
 *
 * Concrete implementations should be provided by consumer code and resolved to
 * user-facing strings on the UI layer.
 */
interface ValidationError

/**
 * Represents a result of validation.
 */
sealed class ValidationResult {

    /**
     * An input is valid.
     */
    data object Valid : ValidationResult()

    /**
     * Validation was skipped.
     */
    data object Skipped : ValidationResult()

    /**
     * An input is invalid.
     */
    data class Invalid(val error: ValidationError) : ValidationResult()
}
