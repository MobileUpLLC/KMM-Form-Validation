package ru.mobileup.sesame.kmm.android_sample.ui

import ru.mobileup.sesame.kmm.form.control.OffsetMapping
import ru.mobileup.sesame.kmm.form.control.TransformedText
import ru.mobileup.sesame.kmm.form.control.VisualTransformation

object RussianPhoneNumberVisualTransformation : VisualTransformation {
    private const val FIRST_HARDCODE_SLOT = "+7 ("
    private const val SECOND_HARDCODE_SLOT = ") "
    private const val DECORATE_HARDCODE_SLOT = "-"

    override fun filter(text: String): TransformedText {
        val trimmed = if (text.length >= 10) text.substring(0..9) else text
        var output = ""
        if (text.isNotEmpty()) output += FIRST_HARDCODE_SLOT
        for (i in trimmed.indices) {
            output += trimmed[i]
            when (i) {
                2 -> output += SECOND_HARDCODE_SLOT
                5 -> output += DECORATE_HARDCODE_SLOT
                7 -> output += DECORATE_HARDCODE_SLOT
            }
        }

        val numberOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0) return offset
                if (offset <= 2) return offset + 4
                if (offset <= 5) return offset + 6
                if (offset <= 7) return offset + 7
                if (offset <= 9) return offset + 8
                return 18
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 7) return offset - 4
                if (offset <= 11) return offset - 6
                if (offset <= 15) return offset - 7
                return 10
            }
        }

        return TransformedText(output, numberOffsetTranslator)
    }
}