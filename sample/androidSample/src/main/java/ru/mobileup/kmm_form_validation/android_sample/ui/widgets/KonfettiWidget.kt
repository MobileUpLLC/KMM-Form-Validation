package ru.mobileup.kmm_form_validation.android_sample.ui.widgets

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.viewinterop.AndroidView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import ru.mobileup.kmm_form_validation.android_sample.R

@Composable
fun KonfettiWidget(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier) {
        val widthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val colors = listOf(
            colorResource(id = R.color.orange).toArgb(),
            colorResource(id = R.color.purple).toArgb(),
            colorResource(id = R.color.pink).toArgb(),
            colorResource(id = R.color.red).toArgb()
        )

        AndroidView(
            modifier = Modifier.matchParentSize(),
            factory = { context ->
                KonfettiView(context).apply {
                    build()
                        .addColors(colors)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.Square, Shape.Circle)
                        .addSizes(Size(12))
                        .setPosition(-50f, widthPx + 50f, -50f, -50f)
                        .streamFor(300, Long.MAX_VALUE)
                }
            }
        )
    }
}
