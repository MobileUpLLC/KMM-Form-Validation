package ru.mobileup.kmm_form_validation.options

/**
 * Values representing the different available Keyboard Types.
 */
enum class KeyboardType {

    /**
     * A keyboard type used to request an IME that shows regular keyboard.
     */
    Text,

    /**
     * A keyboard type used to request an IME that is capable of inputting ASCII characters.
     */
    Ascii,

    /**
     * A keyboard type used to request an that is capable of inputting digits.
     */
    Number,

    /**
     * A keyboard type used to request an IME that is capable of inputting phone numbers.
     */
    Phone,

    /**
     * A keyboard type used to request an IME that is capable of inputting URIs.
     */
    Uri,

    /**
     * A keyboard type used to request an IME that is capable of inputting email addresses.
     */
    Email,

    /**
     * A keyboard type used to request an IME that is capable of inputting password
     */
    Password,

    /**
     * A keyboard type used to request an IME that is capable of inputting number password.
     */
    NumberPassword,
}