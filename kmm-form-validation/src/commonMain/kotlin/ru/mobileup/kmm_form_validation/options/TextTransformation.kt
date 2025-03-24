package ru.mobileup.kmm_form_validation.options

/**
 * Allows to apply text transformation (such as filtering).
 */
fun interface TextTransformation {

    fun transform(text: String): String

    companion object {
        /**
         * Chains multiple text transformations together, applying them sequentially.
         */
        fun chain(vararg transformations: TextTransformation) = TextTransformation { text ->
            transformations.fold(text) { acc, transformation ->
                transformation.transform(acc)
            }
        }
    }
}

/**
 * A text transformation that allows only digits in the input string.
 * If any non-digit characters are removed, it triggers the [onCorrected] callback.
 *
 * @property onCorrected A callback triggered when non-digit characters are removed.
 */
class OnlyDigitsTextTransformation(
    private val onCorrected: () -> Unit = {},
) : TextTransformation {

    override fun transform(text: String): String {
        val filteredText = text.filter(Char::isDigit)
        if (filteredText != text) onCorrected()
        return filteredText
    }
}

/**
 * A text transformation that limits the input string to a maximum length.
 * If the input exceeds [maxLength], it is truncated, and [onLimitExceeded] is triggered.
 *
 * @property maxLength The maximum allowed length for the input text.
 * @property onLimitExceeded A callback triggered when the length exceeds [maxLength].
 */
class MaxLengthTextTransformation(
    private val maxLength: Int,
    private val onLimitExceeded: () -> Unit = {},
) : TextTransformation {

    override fun transform(text: String): String {
        if (text.length > maxLength) onLimitExceeded()
        return text.take(maxLength)
    }
}
