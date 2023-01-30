package ru.mobileup.sesame.kmm.android_sample.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.flow.collectLatest
import ru.mobileup.sesame.kmm.form.control.CheckControl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckboxField(
    checkControl: CheckControl,
    label: String,
    modifier: Modifier = Modifier
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    val checked by checkControl.checked.collectAsState()
    val enabled by checkControl.enabled.collectAsState()
    val error by checkControl.error.collectAsState()

    Column(modifier = modifier.fillMaxWidth()) {

        LaunchedEffect(key1 = checkControl) {
            checkControl.scrollToItEvent.collectLatest {
                bringIntoViewRequester.bringIntoView()
            }
        }
        
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checkControl.onCheckedChanged(it) },
                enabled = enabled
            )

            Text(text = label)
        }

        ErrorText(
            error?.localized() ?: "",
            paddingValues = PaddingValues(horizontal = 16.dp)
        )
    }
}