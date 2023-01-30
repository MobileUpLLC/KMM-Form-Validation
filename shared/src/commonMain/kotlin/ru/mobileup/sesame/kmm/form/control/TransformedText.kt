package ru.mobileup.sesame.kmm.form.control


/**
 * The transformed text with offset offset mapping
 */
data class TransformedText(
    /**
     * The transformed text
     */
    val text: String,

    /**
     * The map used for bidirectional offset mapping from original to transformed text.
     */
    val offsetMapping: OffsetMapping
)