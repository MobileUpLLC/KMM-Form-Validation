# KMM Form Validation
[![Maven Central](https://img.shields.io/maven-central/v/ru.mobileup/kmm-form-validation)](https://repo1.maven.org/maven2/ru/mobileup/kmm-form-validation)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Kotlin Multiplatform library to control and validate forms. This library is based on [sesame-form](https://github.com/aartikov/Sesame/tree/master/sesame-form).

## Installation
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("ru.mobileup:kmm-form-validation:$form_version")
}
```

## Usage

### Controls

[Controls](https://github.com/MobileUpLLC/KMM-Form-Validation/blob/main/kmm-form-validation/src/commonMain/kotlin/ru/mobileup/kmm_form_validation/control/UIControl.kt)
are the building blocks for creating validatable forms. The library provides the [InputControl](https://github.com/MobileUpLLC/KMM-Form-Validation/blob/main/kmm-form-validation/src/commonMain/kotlin/ru/mobileup/kmm_form_validation/control/InputControl.kt)
for managing text input values, the [CheckControl](https://github.com/MobileUpLLC/KMM-Form-Validation/blob/main/kmm-form-validation/src/commonMain/kotlin/ru/mobileup/kmm_form_validation/control/CheckControl.kt) 
for handling boolean input values, and the [PickerControl](https://github.com/MobileUpLLC/KMM-Form-Validation/blob/main/kmm-form-validation/src/commonMain/kotlin/ru/mobileup/kmm_form_validation/control/PickerControl.kt) for managing selectable input values, such as dropdowns or pickers.

These controls represent the logical structure of UI elements, allowing for state management and validation logic to be separated from the UI layer.

To ensure proper state management and lifecycle handling, these controls should be instantiated within a state-holder entity, such as a `ViewModel` or a [Decompose](https://github.com/arkivanov/Decompose) component. This ensures that the controls’ state is properly scoped and persists across configuration changes.

### Creating an InputControl

`InputControl` is designed to manage text input with additional features such as text transformation, visual transformation, length restrictions, and keyboard options.

```kotlin
class MyViewModel : ViewModel() {
    
    val nameInput = InputControl(
        coroutineScope = viewModelScope,
        maxLength = NAME_MAX_LENGTH,
        textTransformation = OnlyLettersTextTransformation,
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next
        )
    )
}
```

### Using InputControl in UI

You can integrate `InputControl` with Jetpack Compose `TextField` as follows:

```kotlin
@Composable
fun NameField(inputControl: InputControl) {
    val text by inputControl.value.collectAsState()
    val enabled by inputControl.enabled.collectAsState()

    TextField(
        value = text,
        onValueChange = inputControl::onValueChange,
        enabled = enabled,
        visualTransformation = inputControl.visualTransformation.toCompose(),
    )
}
```

### Creating a CheckControl

`CheckControl` is designed for managing boolean inputs, such as checkboxes.

```kotlin
class MyViewModel : ViewModel() {

    val termsCheckBox = CheckControl(viewModelScope)
}
```

### Using CheckControl in UI

You can integrate `CheckControl` with Jetpack Compose `Checkbox` as follows:

```kotlin
@Composable
fun TermsCheckbox(checkControl: CheckControl) {
    val checked by checkControl.value.collectAsState()
    val enabled by checkControl.enabled.collectAsState()

    Checkbox(
        checked = checked,
        onCheckedChange = checkControl::onValueChange,
        enabled = enabled
    )
}
```

### Creating a PickerControl

`PickerControl<T>` is designed for managing selectable inputs, such as dropdowns or pickers.
If the initial value is set to `null`, it means that no value has been selected yet.

```kotlin
enum class Gender {
    Male, Female, Other;

    val displayStringDesc: StringDesc
        get() = StringDesc.Raw(name)
}

class MyViewModel : ViewModel() {

    val genderPicker = PickerControl<Gender>(viewModelScope) { it?.displayStringDesc }
}
```

### Using PickerControl in UI

You can integrate `PickerControl<T>` with Jetpack Compose as follows:

```kotlin
@Composable
fun GenderPicker(pickerControl: PickerControl<Gender>) {
    val displayValue by pickerControl.displayValue.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Column {
        TextButton(onClick = { expanded = true }) {
            Text(displayValue?.localized() ?: "Select gender")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            Gender.entries.forEach { gender ->
                DropdownMenuItem(onClick = {
                    pickerControl.onValueChange(gender)
                    expanded = false
                }) {
                    Text(gender.name)
                }
            }
        }
    }
}
```

### FormValidation

`FormValidator` is a utility class designed to manage and validate multiple controls in a form. 
It ensures that user inputs meet specific validation criteria and provides dynamic updates on the form’s validation state.

#### Key Features:

• Validates multiple controls simultaneously\
• Supports dynamic validation updates via `StateFlow`\
• Supports both required and optional validation rules\
• Can be extended with additional validation features

#### Creating a FormValidator

To create a `FormValidator`, use the `formValidator` DSL inside a `CoroutineScope`.

```kotlin
val formValidator = viewModelScope.formValidator {
    features = listOf(
        ValidateOnFocusLost,
        RevalidateOnValueChanged,
        SetFocusOnFirstInvalidControlAfterValidation
    )

    input(nameInput, required = false) {
        isNotBlank(StringDesc.Raw("Please fill this field"))
    }
    
    picker(genderPicker) {
        isPicked(StringDesc.Raw("Please select your gender"))
    }

    input(emailInput) {
        isNotBlank(StringDesc.Raw("Please fill this field"))
        regex(
            regex = EMAIL_REGEX_PATTERN.toRegex(),
            errorMessageRes = StringDesc.Raw("Invalid e-mail address")
        )
    }

    checked(termsCheckBox, StringDesc.Raw("Please accept the terms of use"))
}
```

#### Handling Form validation state

To track form validation, use `validationState`. This state updates dynamically whenever a field’s value or validation status changes, allowing UI components to react accordingly. 
Additionally, you can track whether the form is completely filled `isFilledState` or contains any errors `hasErrorState`.

```kotlin
class MyViewModel : ViewModel() {
    
    val formValidator = viewModelScope.formValidator { /*...*/ }
    
    val isFormValid = computed(formValidator.validationState) { it.isValid }
    
    val isLoginButtonEnabled = computed(
        formValidator.isFilledState,
        formValidator.hasErrorState
    ) { isFilled, hasError -> isFilled && !hasError }
}

@Composable
fun Screen(viewModel: MyViewModel) {
    val isLoginButtonEnabled by viewModel.isLoginButtonEnabled.collectAsState()
    
    Button(
        text = "Login",
        enabled = isLoginButtonEnabled,
        onClick = { /*...*/ }
    )
}
```

• `validationState`: This state reflects the current validation result of the entire form. It updates whenever any control’s value or validation status changes.\
• `isFilledState`: Tracks whether all required fields are filled. A field is considered filled if it contains valid input or is skipped in validation.\
• `hasErrorState`: This state indicates whether the form contains any validation errors. The form has errors if any control has a non-null error and is not skipped in validation.

#### Manual Form Validation

The `validate()` method allows you to manually trigger form validation, ensuring that all input fields meet their validation criteria before proceeding with an action.
Unlike reactive validation tracking, which only observes changes without displaying errors, this method performs validation and immediately updates the fields with error messages.
This makes it useful for scenarios where user input needs to be explicitly validated and reflected in the UI.

```kotlin
class MyViewModel : ViewModel() {

    val formValidator = viewModelScope.formValidator { /*...*/ }

    fun onLoginClick() {
        if (formValidator.validate().isValid) {
            /*perform action*/
        } else {
            /*show error*/
        }
    }
}
```

## Advanced Usage
For advanced usage, you can refer to the [Sample](https://github.com/MobileUpLLC/KMM-Form-Validation/tree/main/sample).
The sample demonstrates how to integrate `InputControl`, `CheckControl`, and `PickerControl<T>` in a real-world scenario using the [Decompose](https://github.com/arkivanov/Decompose) library.

## License

```
MIT License

Copyright (c) 2025 MobileUp

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```