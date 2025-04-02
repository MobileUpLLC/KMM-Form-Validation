package ru.mobileup.kmm_form_validation.android_sample.ui.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import ru.mobileup.kmm_form_validation.android_sample.R
import ru.mobileup.kmm_form_validation.control.PickerControl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PickerField(
    pickerControl: PickerControl<*>,
    onClick: () -> Unit,
    label: String,
    isExpanded: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val displayValue by pickerControl.displayValue.collectAsState()
    val error by pickerControl.error.collectAsState()
    val enabled by pickerControl.enabled.collectAsState()

    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) -180f else 0f,
        animationSpec = tween(durationMillis = 200)
    )

    LaunchedEffect(Unit) {
        pickerControl.scrollToItEvent.collect {
            bringIntoViewRequester.bringIntoView()
        }
    }

    Column(
        modifier
            .graphicsLayer { alpha = if (enabled) 1f else 0.5f }
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .border(
                    1.dp,
                    if (error != null) {
                        MaterialTheme.colors.error
                    } else {
                        MaterialTheme.colors.onSurface.copy(alpha = 0.4f)
                    },
                    RoundedCornerShape(4.dp)
                )
                .clickable(onClick = onClick, enabled = enabled)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Crossfade(
                modifier = Modifier.weight(1f),
                targetState = displayValue
            ) { value ->
                Text(
                    text = value?.localized() ?: label,
                    style = MaterialTheme.typography.subtitle1.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = if (value == null) 0.6f else 1f)
                    )
                )
            }
            Icon(
                modifier = Modifier.graphicsLayer { rotationZ = rotation },
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = if (error != null) {
                    MaterialTheme.colors.error
                } else {
                    MaterialTheme.colors.onSurface.copy(alpha = 0.54f)
                }
            )
        }
        ErrorText(error?.localized() ?: "")
        AnimatedVisibility(isExpanded) {
            content()
        }
    }
}