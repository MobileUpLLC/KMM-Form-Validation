package ru.mobileup.kmm_form_validation.options

/**
 *
 * @param singleLine informs the IME that the text field is single line and IME
 * should not show return key.
 * @param capitalization informs the IME whether to automatically capitalize characters,
 * words or sentences. Only applicable to only text based [KeyboardType]s such as
 * [KeyboardType.Text], [KeyboardType.Ascii]. It will not be applied to [KeyboardType]s such as
 * [KeyboardType.Number].
 * @param autoCorrect informs the IME whether to enable auto correct. Only applicable to
 * text based [KeyboardType]s such as [KeyboardType.Email], [KeyboardType.Uri]. It will not be
 * applied to [KeyboardType]s such as [KeyboardType.Number]. Most of IME
 * implementations ignore this value for [KeyboardType]s such as [KeyboardType.Text].
 * @param keyboardType The keyboard type to be used in this text field. Note that this input type
 * is honored by IME and shows corresponding keyboard but this is not guaranteed. For example,
 * some IME may send non-ASCII character even if you set [KeyboardType.Ascii].
 * @param imeAction The IME action. This IME action is honored by IME and may show specific icons
 * on the keyboard. For example, search icon may be shown if [ImeAction.Search] is specified.
 * When [singleLine] is false, the IME might show return key rather than the action requested here.
 */
data class ImeOptions(
    val singleLine: Boolean = false,
    val capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    val autoCorrect: Boolean = true,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val imeAction: ImeAction = ImeAction.Default
)