package ru.mobileup.sesame.kmm.form.control

fun interface VisualTransformation {
    /**
     * Change the visual output of given text.
     *
     * Note that the returned text length can be different length from the given text. The composable
     * will call the offset translator for converting offsets for various reasons, cursor drawing
     * position, text selection by gesture, etc.
     *
     * @param text The original text
     * @return filtered text.
     */
    fun filter(text: String): TransformedText

    companion object {
        /**
         * A special visual transformation object indicating that no transformation is applied.
         */
        val None: VisualTransformation = VisualTransformation { text ->
            TransformedText(text, OffsetMapping.Identity)
        }
    }
}

/**
 * The Visual Filter can be used for password Input Field.
 *
 * Note that this visual filter only works for ASCII characters.
 *
 * @param mask The mask character used instead of original text.
 */
data class PasswordVisualTransformation(val mask: Char = '\u2022') : VisualTransformation {
    constructor() : this(mask = '\u2022')

    override fun filter(text: String): TransformedText {
        return TransformedText(mask.toString().repeat(text.length), OffsetMapping.Identity)
    }
}