package ru.mobileup.sesame.kmm.android_sample.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.graphics.colorInt
import dev.icerock.moko.resources.ColorResource
import dev.icerock.moko.resources.getColor
import androidx.compose.ui.graphics.Color as ComposeColor

@Composable
fun ColorResource.getColor(): ComposeColor {
    val context = LocalContext.current
    val color = this.getColor(context)
    return ComposeColor(color.colorInt())
}