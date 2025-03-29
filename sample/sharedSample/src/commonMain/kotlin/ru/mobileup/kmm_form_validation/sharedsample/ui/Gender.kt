package ru.mobileup.kmm_form_validation.sharedsample.ui

import dev.icerock.moko.resources.desc.ResourceStringDesc
import dev.icerock.moko.resources.desc.desc
import ru.mobileup.kmm_form_validation.sharedsample.MR

enum class Gender {
    Male, Female, Other;

    val displayValueDesc: ResourceStringDesc
        get() = when (this) {
            Male -> MR.strings.gender_male
            Female -> MR.strings.gender_female
            Other -> MR.strings.gender_other
        }.desc()
}
