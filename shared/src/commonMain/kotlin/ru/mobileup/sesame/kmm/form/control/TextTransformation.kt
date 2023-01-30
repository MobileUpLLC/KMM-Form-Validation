package ru.mobileup.sesame.kmm.form.control

/**
 * Allows to apply text transformation (such as filtering).
 */
fun interface TextTransformation {

    fun transform(text: String): String
}