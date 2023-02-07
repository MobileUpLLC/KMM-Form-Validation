package ru.mobileup.kmm_form_validation.android_sample.ui.widgets

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import dev.icerock.moko.resources.compose.localized
import kotlinx.coroutines.flow.collectLatest
import ru.mobileup.kmm_form_validation.android_sample.R
import ru.mobileup.kmm_form_validation.toCompose

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PasswordTextField(
    inputControl: ru.mobileup.kmm_form_validation.control.InputControl,
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
            keyboardOptions = inputControl.keyboardOptions.toCompose(),
            singleLine = inputControl.singleLine,
            label = {
                Text(text = label)
            },
            isError = error != null,
            onValueChange = inputControl::onTextChanged,
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                inputControl.visualTransformation.toCompose()
            },
            trailingIcon = {
                val image = if (passwordVisibility) {
                    painterResource(id = R.drawable.ic_visibility_on)
                } else {
                    painterResource(id = R.drawable.ic_visibility_off)
                }

                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(image, null)
                }
            },
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