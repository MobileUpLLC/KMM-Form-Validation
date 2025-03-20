package ru.mobileup.kmm_form_validation.options

data class KeyboardOptions(
    val capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    val autoCorrect: Boolean = true,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val imeAction: ImeAction = ImeAction.Default
) {
    constructor() : this(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = true,
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Default
    )
}