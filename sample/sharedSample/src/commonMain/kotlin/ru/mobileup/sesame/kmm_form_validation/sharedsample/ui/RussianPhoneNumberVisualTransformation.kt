package ru.mobileup.sesame.kmm_form_validation.sharedsample.ui

import ru.mobileup.sesame.kmm_form_validation.form.control.OffsetMapping
import ru.mobileup.sesame.kmm_form_validation.form.control.TransformedText
import ru.mobileup.sesame.kmm_form_validation.form.control.VisualTransformation

object RussianPhoneNumberVisualTransformation : VisualTransformation {
    private const val FIRST_HARDCODE_SLOT = "+7"
    private const val SECOND_HARDCODE_SLOT = " ("
    private const val THIRD_HARDCODE_SLOT = ") "
    private const val DECORATE_HARDCODE_SLOT = "-"

    override fun filter(text: String): TransformedText {
        var hasPrefix = false
        var output = ""
        if (text.isNotEmpty()) {
            if (text.startsWith(FIRST_HARDCODE_SLOT)) {
                hasPrefix = true
            }
            output += FIRST_HARDCODE_SLOT
            output += SECOND_HARDCODE_SLOT
        }

        var trimmed = if (hasPrefix) text.drop(2) else text
        trimmed = if (trimmed.length >= 10) trimmed.substring(0 until 10) else trimmed

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

    override fun restore(text: String): String {
        return text
            .replaceFirst(FIRST_HARDCODE_SLOT, "")
            .replaceFirst(SECOND_HARDCODE_SLOT, "")
            .replaceFirst(THIRD_HARDCODE_SLOT, "")
            .replace(DECORATE_HARDCODE_SLOT, "")
    }
}