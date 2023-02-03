package ru.mobileup.sesame.kmm_form_validation.form.control

/**
 * Allows to apply text transformation (such as filtering).
 */
fun interface TextTransformation {
    fun transform(text: String): String
}