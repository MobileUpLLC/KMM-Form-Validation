package ru.mobileup.kmm_form_validation.options

interface VisualTransformation {
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
        val None: VisualTransformation = object : VisualTransformation {
            override fun filter(text: String): TransformedText {
                return TransformedText(text, OffsetMapping.Identity)
            }

            override fun restore(text: String): String {
                return text
            }
        }
    }

    fun restore(text: String): String
}