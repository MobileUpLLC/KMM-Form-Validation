package ru.mobileup.kmm_form_validation.sharedsample.ui

import ru.mobileup.kmm_form_validation.options.OffsetMapping
import ru.mobileup.kmm_form_validation.options.TransformedText
import ru.mobileup.kmm_form_validation.options.VisualTransformation

object RussianPhoneNumberVisualTransformation : VisualTransformation {
    private const val FIRST_HARDCODE_SLOT = "+7"
    private const val SECOND_HARDCODE_SLOT = " ("
    private const val THIRD_HARDCODE_SLOT = ") "
    private const val DECORATE_HARDCODE_SLOT = "-"

    // "" -> "+7"
    // "ABCDEFGHIJ" -> "+7 (ABC) DEF-GH-IJ"

    override fun filter(text: String): TransformedText {
        val trimmed = text.take(10)
        var output = ""

        output += FIRST_HARDCODE_SLOT
        if (trimmed.isNotEmpty()) {
            output += SECOND_HARDCODE_SLOT
        }

        for (i in trimmed.indices) {
            output += trimmed[i]
            when (i) {
                2 -> output += THIRD_HARDCODE_SLOT
                5 -> output += DECORATE_HARDCODE_SLOT
                7 -> output += DECORATE_HARDCODE_SLOT
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (trimmed.isEmpty()) {
                    return 2
                }

                return when {
                    offset < 3 -> offset + 4
                    offset < 6 -> offset + 6
                    offset < 8 -> offset + 7
                    else -> offset + 8
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (trimmed.isEmpty()) {
                    return 0
                }

                return when {
                    offset < 4 -> 0
                    offset < 7 -> offset - 4
                    offset < 8 -> offset - 5
                    offset < 12 -> offset - 6
                    offset < 15 -> offset - 7
                    else -> offset - 8
                }
            }
        }

        return TransformedText(output, numberOffsetTranslator)
    }

    override fun restore(text: String): String {
        return text
            .replaceFirst(FIRST_HARDCODE_SLOT, "")
            .replaceFirst(SECOND_HARDCODE_SLOT, "")
            .replaceFirst(THIRD_HARDCODE_SLOT, "")
            .replace(DECORATE_HARDCODE_SLOT, "")
    }
}
