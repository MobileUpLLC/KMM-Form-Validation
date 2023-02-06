package ru.mobileup.kmm_form_validation.options

/**
 * Allows to apply text transformation (such as filtering).
 */
fun interface TextTransformation {
    fun transform(text: String): String
}