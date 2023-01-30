package ru.mobileup.sesame.kmm.android_sample.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.flow.collectLatest
import ru.mobileup.sesame.kmm.android_sample.utils.asCompose
import ru.mobileup.sesame.kmm.form.control.InputControl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TextField(
    inputControl: InputControl,
    label: String,
    modifier: Modifier = Modifier
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val hasFocus by inputControl.hasFocus.collectAsState()
    val error by inputControl.error.collectAsState()
    val text by inputControl.text.collectAsState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        val focusRequester = remember { FocusRequester() }

        if (hasFocus) {
            SideEffect {
                focusRequester.requestFocus()
            }
        }

        LaunchedEffect(key1 = inputControl) {
            inputControl.scrollToItEvent.collectLatest {
                bringIntoViewRequester.bringIntoView()
            }
        }

        OutlinedTextField(
            value = text,
            keyboardOptions = inputControl.keyboardOptions.asCompose(),
            singleLine = inputControl.singleLine,
            label = { Text(text = label) },
            onValueChange = inputControl::onTextChanged,
            isError = error != null,
            visualTransformation = inputControl.visualTransformation.asCompose(),
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    inputControl.onFocusChanged(it.isFocused)
                }
        )

        ErrorText(error?.localized() ?: "")
    }
}