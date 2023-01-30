package ru.mobileup.sesame.kmm.android_sample.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import ru.mobileup.sesame.kmm.form.control.OffsetMapping
import ru.mobileup.sesame.kmm.form.control.VisualTransformation
import ru.mobileup.sesame.kmm.form.options.ImeAction
import ru.mobileup.sesame.kmm.form.options.KeyboardCapitalization
import ru.mobileup.sesame.kmm.form.options.KeyboardOptions
import ru.mobileup.sesame.kmm.form.options.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions as ComposeKeyboardOptions
import androidx.compose.ui.text.input.ImeAction as ComposeImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization as ComposeKeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyBoardType
import androidx.compose.ui.text.input.OffsetMapping as ComposeOffsetMapping
import androidx.compose.ui.text.input.VisualTransformation as ComposeVisualTransformation

fun VisualTransformation.asCompose(): ComposeVisualTransformation {
    return ComposeVisualTransformation {
        val transformedText = filter(it.text)
        TransformedText(
            AnnotatedString(transformedText.text),
            transformedText.offsetMapping.asCompose()
        )
    }
}

fun OffsetMapping.asCompose(): ComposeOffsetMapping {
    return object : ComposeOffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            return this@asCompose.originalToTransformed(offset)
        }

        override fun transformedToOriginal(offset: Int): Int {
            return this@asCompose.transformedToOriginal(offset)
        }
    }
}

fun KeyboardOptions.asCompose(): ComposeKeyboardOptions {
    return ComposeKeyboardOptions(
        capitalization = capitalization.asCompose(),
        autoCorrect = autoCorrect,
        keyboardType = keyboardType.asCompose(),
        imeAction = imeAction.asCompose()
    )
}

fun KeyboardCapitalization.asCompose(): ComposeKeyboardCapitalization {
    return when (this) {
        KeyboardCapitalization.None -> ComposeKeyboardCapitalization.None
        KeyboardCapitalization.Characters -> ComposeKeyboardCapitalization.Characters
        KeyboardCapitalization.Words -> ComposeKeyboardCapitalization.Words
        KeyboardCapitalization.Sentences -> ComposeKeyboardCapitalization.Sentences
        else -> throw NotImplementedError("Can't find compose equivalent of KeyboardCapitalization - $this")
    }
}

fun KeyboardType.asCompose(): ComposeKeyBoardType {
    return when (this) {
        KeyboardType.Text -> ComposeKeyBoardType.Text
        KeyboardType.Ascii -> ComposeKeyBoardType.Ascii
        KeyboardType.Email -> ComposeKeyBoardType.Email
        KeyboardType.Uri -> ComposeKeyBoardType.Uri
        KeyboardType.Number -> ComposeKeyBoardType.Number
        KeyboardType.NumberPassword -> ComposeKeyBoardType.NumberPassword
        KeyboardType.Password -> ComposeKeyBoardType.Password
        KeyboardType.Phone -> ComposeKeyBoardType.Phone
        else -> throw NotImplementedError("Can't find compose equivalent of KeyboardType - $this")
    }
}

fun ImeAction.asCompose(): ComposeImeAction {
    return when (this) {
        ImeAction.Default -> ComposeImeAction.Default
        ImeAction.None -> ComposeImeAction.None
        ImeAction.Search -> ComposeImeAction.Search
        ImeAction.Go -> ComposeImeAction.Go
        ImeAction.Done -> ComposeImeAction.Done
        ImeAction.Next -> ComposeImeAction.Next
        ImeAction.Send -> ComposeImeAction.Send
        ImeAction.Previous -> ComposeImeAction.Previous
        else -> throw NotImplementedError("Can't find compose equivalent of ImeAction - $this")
    }
}

