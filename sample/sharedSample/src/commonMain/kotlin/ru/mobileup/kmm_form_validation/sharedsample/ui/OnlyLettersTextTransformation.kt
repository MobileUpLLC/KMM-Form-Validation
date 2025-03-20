package ru.mobileup.kmm_form_validation.sharedsample.ui

import ru.mobileup.kmm_form_validation.options.TextTransformation

object OnlyLettersTextTransformation : TextTransformation {

    override fun transform(text: String): String = text.filter(Char::isLetter)
}
