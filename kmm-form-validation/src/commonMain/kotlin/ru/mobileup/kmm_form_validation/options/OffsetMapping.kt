package ru.mobileup.kmm_form_validation.options

/**
 * Provides bidirectional offset mapping between original and transformed text.
 */
interface OffsetMapping {
    /**
     * Convert offset in original text into the offset in transformed text.
     *
     * This function must be a monotonically non-decreasing function. In other words, if a cursor
     * advances in the original text, the cursor in the transformed text must advance or stay there.
     *
     * @param offset offset in original text.
     * @return offset in transformed text
     *
     * @see VisualTransformation
     */
    fun originalToTransformed(offset: Int): Int

    /**
     * Convert offset in transformed text into the offset in original text.
     *
     * This function must be a monotonically non-decreasing function. In other words, if a cursor
     * advances in the transformed text, the cusrsor in the original text must advance or stay
     * there.
     *
     * @param offset offset in transformed text
     * @return offset in original text
     *
     * @see VisualTransformation
     */
    fun transformedToOriginal(offset: Int): Int

    companion object {
        /**
         * The offset map used for identity mapping.
         */
        val Identity = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int = offset
            override fun transformedToOriginal(offset: Int): Int = offset
        }
    }
}