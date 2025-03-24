package ru.mobileup.kmm_form_validation.android_sample.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mobileup.kmm_form_validation.android_sample.R
import ru.mobileup.kmm_form_validation.android_sample.ui.theme.AppTheme
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.CheckboxField
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.KonfettiWidget
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.MenuButton
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.PasswordTextField
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.TextField
import ru.mobileup.kmm_form_validation.options.VisualTransformation
import ru.mobileup.kmm_form_validation.sharedsample.MR
import ru.mobileup.kmm_form_validation.sharedsample.ui.FakeFormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.SubmitButtonState

@Composable
fun FormUi(
    component: FormComponent,
    modifier: Modifier = Modifier,
) {
    val showConfetti by component.showConfetti.collectAsState()
    val submitButtonState by component.submitButtonState.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        color = MaterialTheme.colors.background
    ) {
        if (showConfetti) KonfettiWidget()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                inputControl = component.nameInput,
                label = stringResource(id = MR.strings.name_hint.resourceId),
            )

            TextField(
                inputControl = component.emailInput,
                label = stringResource(id = MR.strings.email_hint.resourceId),
            )

            val phone by component.phoneInput.text.collectAsState()
            val phoneHasFocus by component.phoneInput.hasFocus.collectAsState()

            TextField(
                inputControl = component.phoneInput,
                label = stringResource(id = MR.strings.phone_hint.resourceId),
                visualTransformation = VisualTransformation.None.takeIf { phone.isEmpty() && !phoneHasFocus }
            )

            PasswordTextField(
                inputControl = component.passwordInput,
                label = stringResource(id = MR.strings.password_hint.resourceId),
            )

            PasswordTextField(
                inputControl = component.confirmPasswordInput,
                label = stringResource(id = MR.strings.confirm_password_hint.resourceId),
            )

            CheckboxField(
                checkControl = component.termsCheckBox,
                label = stringResource(id = MR.strings.terms_hint.resourceId)
            )

            val backgroundColor by animateColorAsState(
                when (submitButtonState) {
                    SubmitButtonState.Valid -> colorResource(R.color.green)
                    SubmitButtonState.Invalid -> colorResource(R.color.red)
                }
            )

            MenuButton(
                text = stringResource(MR.strings.submit_button.resourceId),
                onClick = component::onSubmitClicked,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = backgroundColor,
                    contentColor = colorResource(R.color.white)
                ),
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FormUiPreview() {
    AppTheme {
        FormUi(FakeFormComponent())
    }
}