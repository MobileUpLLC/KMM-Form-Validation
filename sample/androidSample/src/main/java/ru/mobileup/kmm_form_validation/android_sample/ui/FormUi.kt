package ru.mobileup.kmm_form_validation.android_sample.ui

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
import ru.mobileup.kmm_form_validation.android_sample.R
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.*
import ru.mobileup.kmm_form_validation.sharedsample.MR
import ru.mobileup.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.SubmitButtonState

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
                    label = stringResource(id = MR.strings.name_hint.resourceId),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TextField(
                    component.emailInput,
                    label = stringResource(id = MR.strings.email_hint.resourceId),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                TextField(
                    component.phoneInput,
                    label = stringResource(id = MR.strings.phone_hint.resourceId),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                PasswordTextField(
                    component.passwordInput,
                    label = stringResource(id = MR.strings.password_hint.resourceId),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                PasswordTextField(
                    component.confirmPasswordInput,
                    label = stringResource(id = MR.strings.confirm_password_hint.resourceId),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                CheckboxField(
                    component.termsCheckBox,
                    label = stringResource(id = MR.strings.terms_hint.resourceId)
                )

                val color = when (submitButtonState) {
                    SubmitButtonState.Valid -> colorResource(R.color.green)
                    SubmitButtonState.Invalid -> colorResource(R.color.red)
                }

                MenuButton(
                    text = stringResource(MR.strings.submit_button.resourceId),
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