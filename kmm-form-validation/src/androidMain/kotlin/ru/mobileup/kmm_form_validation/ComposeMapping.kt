package ru.mobileup.kmm_form_validation

import ru.mobileup.kmm_form_validation.options.ImeAction
import ru.mobileup.kmm_form_validation.options.KeyboardCapitalization
import ru.mobileup.kmm_form_validation.options.KeyboardOptions
import ru.mobileup.kmm_form_validation.options.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions as ComposeKeyboardOptions
import androidx.compose.ui.text.input.ImeAction as ComposeImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization as ComposeKeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType as ComposeKeyBoardType

fun KeyboardOptions.toCompose(): ComposeKeyboardOptions {
    return ComposeKeyboardOptions(
        capitalization = capitalization.toCompose(),
        autoCorrectEnabled = autoCorrect,
        keyboardType = keyboardType.toCompose(),
        imeAction = imeAction.toCompose()
    )
}

fun KeyboardCapitalization.toCompose(): ComposeKeyboardCapitalization {
    return when (this) {
        KeyboardCapitalization.None -> ComposeKeyboardCapitalization.None
        KeyboardCapitalization.Characters -> ComposeKeyboardCapitalization.Characters
        KeyboardCapitalization.Words -> ComposeKeyboardCapitalization.Words
        KeyboardCapitalization.Sentences -> ComposeKeyboardCapitalization.Sentences
    }
}

fun KeyboardType.toCompose(): ComposeKeyBoardType {
    return when (this) {
        KeyboardType.Text -> ComposeKeyBoardType.Text
        KeyboardType.Ascii -> ComposeKeyBoardType.Ascii
        KeyboardType.Email -> ComposeKeyBoardType.Email
        KeyboardType.Uri -> ComposeKeyBoardType.Uri
        KeyboardType.Number -> ComposeKeyBoardType.Number
        KeyboardType.NumberPassword -> ComposeKeyBoardType.NumberPassword
        KeyboardType.Password -> ComposeKeyBoardType.Password
        KeyboardType.Phone -> ComposeKeyBoardType.Phone
    }
}

fun ImeAction.toCompose(): ComposeImeAction {
    return when (this) {
        ImeAction.Default -> ComposeImeAction.Default
        ImeAction.None -> ComposeImeAction.None
        ImeAction.Search -> ComposeImeAction.Search
        ImeAction.Go -> ComposeImeAction.Go
        ImeAction.Done -> ComposeImeAction.Done
        ImeAction.Next -> ComposeImeAction.Next
        ImeAction.Send -> ComposeImeAction.Send
        ImeAction.Previous -> ComposeImeAction.Previous
    }
}
