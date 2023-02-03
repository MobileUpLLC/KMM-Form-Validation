package ru.mobileup.sesame.kmm_form_validation.control

/**
 * Allows to apply text transformation (such as filtering).
 */
fun interface TextTransformation {
    fun transform(text: String): String
}