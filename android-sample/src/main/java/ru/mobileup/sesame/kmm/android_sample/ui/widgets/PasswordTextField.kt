package ru.mobileup.sesame.kmm.android_sample.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.PasswordVisualTransformation
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.flow.collectLatest
import ru.mobileup.sesame.kmm.android_sample.utils.asCompose
import ru.mobileup.sesame.kmm.form.control.InputControl

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordTextField(
    inputControl: InputControl,
    label: String,
    modifier: Modifier = Modifier
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewRequester)
    ) {
        val focusRequester = remember { FocusRequester() }

        var passwordVisibility by remember { mutableStateOf(false) }

        val hasFocus by inputControl.hasFocus.collectAsState()
        val error by inputControl.error.collectAsState()
        val text by inputControl.text.collectAsState()

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
            label = {
                Text(text = label)
            },
            isError = error != null,
            onValueChange = inputControl::onTextChanged,
            visualTransformation = if (passwordVisibility) {
                inputControl.visualTransformation.asCompose()
            } else {
                PasswordVisualTransformation()
            },
            trailingIcon = {
                val image = if (passwordVisibility) {
                    Icons.Filled.Check
                } else {
                    Icons.Filled.CheckCircle
                }

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(imageVector = image, null)
                }
            },
            modifier = modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    inputControl.onFocusChanged(it.isFocused)
                }
        )

        ErrorText(inputControl.error.value?.localized() ?: "")
    }
}