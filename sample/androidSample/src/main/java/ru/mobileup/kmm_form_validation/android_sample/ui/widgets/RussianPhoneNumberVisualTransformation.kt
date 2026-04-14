package ru.mobileup.kmm_form_validation.android_sample.ui.widgets

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object RussianPhoneNumberVisualTransformation : VisualTransformation {
    private const val FIRST_HARDCODE_SLOT = "+7"
    private const val SECOND_HARDCODE_SLOT = " ("
    private const val THIRD_HARDCODE_SLOT = ") "
    private const val DECORATE_HARDCODE_SLOT = "-"

    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(10)
        val output = buildString {
            append(FIRST_HARDCODE_SLOT)
            if (trimmed.isNotEmpty()) {
                append(SECOND_HARDCODE_SLOT)
            }

            trimmed.forEachIndexed { index, char ->
                append(char)
                when (index) {
                    2 -> append(THIRD_HARDCODE_SLOT)
                    5, 7 -> append(DECORATE_HARDCODE_SLOT)
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(output),
            offsetMapping = object : OffsetMapping {
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
        )
    }
}
