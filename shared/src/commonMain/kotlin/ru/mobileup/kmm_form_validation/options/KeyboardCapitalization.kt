package ru.mobileup.kmm_form_validation.options

/**
 * Options to request software keyboard to capitalize the text. Applies to languages which
 * has upper-case and lower-case letters.
 */
enum class KeyboardCapitalization {
    /**
     * Do not auto-capitalize text.
     */
    None,

    /**
     * Capitalize all characters.
     */
    Characters,

    /**
     * Capitalize the first character of every word.
     */
    Words,

    /**
     * Capitalize the first character of each sentence.
     */
    Sentences
}