package ru.mobileup.sesame.kmm_form_validation.android_sample.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mobileup.sesame.kmm_form_validation.android_sample.R
import ru.mobileup.sesame.kmm_form_validation.android_sample.ui.widgets.*
import ru.mobileup.sesame.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.sesame.kmm_form_validation.sharedsample.ui.SubmitButtonState

@Composable
fun FormUi(
    component: FormComponent,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        BoxWithConstraints(Modifier.fillMaxSize()) {
            KonfettiWidget(maxWidth, component.dropKonfettiEvent, modifier)

            val submitButtonState by component.submitButtonState.collectAsState()

            val scrollState = rememberScrollState()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(vertical = 20.dp, horizontal = 8.dp)
            ) {
                TextField(
                    component.nameInput,
                    label = stringResource(id = R.string.name_hint),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TextField(
                    component.emailInput,
                    label = stringResource(id = R.string.email_hint),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TextField(
                    component.phoneInput,
                    label = stringResource(id = R.string.phone_hint),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                PasswordTextField(
                    component.passwordInput,
                    label = stringResource(id = R.string.password_hint),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                PasswordTextField(
                    component.confirmPasswordInput,
                    label = stringResource(id = R.string.confirm_password_hint),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                CheckboxField(
                    component.termsCheckBox,
                    label = stringResource(id = R.string.terms_hint)
                )

                val color = when (submitButtonState) {
                    SubmitButtonState.Valid -> colorResource(R.color.green)
                    SubmitButtonState.Invalid -> colorResource(R.color.red)
                }

                MenuButton(
                    text = stringResource(R.string.submit_button),
                    onClick = component::onSubmitClicked,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = color,
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}