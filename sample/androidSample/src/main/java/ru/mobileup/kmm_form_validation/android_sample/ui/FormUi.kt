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
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.PickerField
import ru.mobileup.kmm_form_validation.android_sample.ui.widgets.TextField
import ru.mobileup.kmm_form_validation.options.VisualTransformation
import ru.mobileup.kmm_form_validation.sharedsample.ui.FakeFormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.FormComponent
import ru.mobileup.kmm_form_validation.sharedsample.ui.Gender
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
                label = stringResource(id = R.string.name_hint),
            )

            var isExpanded by remember { mutableStateOf(false) }
            val gender by component.genderPicker.value.collectAsState()

            PickerField(
                pickerControl = component.genderPicker,
                selectedValueText = gender?.displayName(),
                onClick = { isExpanded = !isExpanded },
                isExpanded = isExpanded,
                label = stringResource(id = R.string.gender_hint),
            ) {
                Column {
                    Gender.entries.forEach {
                        DropdownMenuItem(
                            onClick = {
                                component.genderPicker.onValueChange(it)
                                isExpanded = false
                            }
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .align(Alignment.CenterVertically),
                                text = it.displayName()
                            )
                            if (it == gender) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    text = "✓",
                                    style = MaterialTheme.typography.h6
                                )
                            }
                        }
                    }
                }
            }

            TextField(
                inputControl = component.emailInput,
                label = stringResource(id = R.string.email_hint),
            )

            val phone by component.phoneInput.value.collectAsState()
            val phoneHasFocus by component.phoneInput.hasFocus.collectAsState()

            TextField(
                inputControl = component.phoneInput,
                label = stringResource(id = R.string.phone_hint),
                visualTransformation = VisualTransformation.None.takeIf { phone.isEmpty() && !phoneHasFocus }
            )

            PasswordTextField(
                inputControl = component.passwordInput,
                label = stringResource(id = R.string.password_hint),
            )

            PasswordTextField(
                inputControl = component.confirmPasswordInput,
                label = stringResource(id = R.string.confirm_password_hint),
            )

            CheckboxField(
                checkControl = component.termsCheckBox,
                label = stringResource(id = R.string.terms_hint)
            )

            CheckboxField(
                checkControl = component.newsletterCheckBox,
                label = stringResource(id = R.string.newsletter_hint)
            )

            val backgroundColor by animateColorAsState(
                when (submitButtonState) {
                    SubmitButtonState.Valid -> colorResource(R.color.green)
                    SubmitButtonState.Invalid -> colorResource(R.color.red)
                }
            )

            MenuButton(
                text = stringResource(R.string.submit_button),
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
