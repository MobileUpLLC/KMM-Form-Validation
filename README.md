# KMM Form Validation
[![Maven Central](https://img.shields.io/maven-central/v/ru.mobileup/kmm-form-validation)](https://repo1.maven.org/maven2/ru/mobileup/kmm-form-validation)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Introduction
The library offers an easy and flexible way to validate text forms.

## Gradle Setup
To use the KMM Form Validation library in your project, add the following dependency to your `build.gradle.kts` file:
```kotlin
implementation("ru.mobileup.kmm-form-validation:1.0.0")
```
or
```kotlin
library("forms", "ru.mobileup", "kmm-form-validation").version("1.0.0")
```

## Usage

### Controls
Controls are the building blocks for validatable forms. KMM Form Validation provides logical representations of UI elements that can be managed from a `ViewModel`.

#### InputControl
Logical representation of an input field. It allows you to configure an input field and manage its state from `ViewModel`.

Usage:
```kotlin
val nameInput = InputControl(
    coroutineScope = viewModelScope,
    initialText = "",
    singleLine = true,
    maxLength = NAME_MAX_LENGTH,
    keyboardOptions = KeyboardOptions(/*...*/),
    textTransformation = null,
    visualTransformation = VisualTransformation.None
)
```

#### CheckControl
Logical representation of a control with a checkable state (`CheckBox`, `Switch`, etc). It allows you to manage the checked state from `ViewModel`.

Usage:
```kotlin
val termsCheckBox = CheckControl(
    coroutineScope = viewModelScope,
    initialChecked = false
)
```

### Form Validation
For validating controls like `InputControl` and `CheckControl`, you can use validators. `Validators` allow you to define validation logic for each control and provide a convenient DSL for creating complex validations.

Example:
```kotlin
class CheckValidator constructor(
    override val control: CheckControl,
    private val validation: (Boolean) -> ValidationResult,
    private val showError: ((StringDesc) -> Unit)? = null
) : ControlValidator<CheckControl> {
    override fun validate(displayResult: Boolean): ValidationResult {
        return getValidationResult().also {
            if (displayResult) displayValidationResult(it)
        }
    }
}
```

### Additional Features
By default, the form validator validates inputs only when `validate()` is called and does not clear errors when inputs are changed. You can enhance this behavior with additional validation features:
- `ValidateOnFocusLost`: Validates an input field automatically when focus is lost.
- `RevalidateOnValueChanged`: Revalidates an input field with an error when its value changes.
- `SetFocusOnFirstInvalidControlAfterValidation`: Sets focus to the first invalid field when `validate()` is called.

Usage:
```kotlin
private val formValidator = formValidator {
    features = listOf(
        ValidateOnFocusLost,
        RevalidateOnValueChanged,
        SetFocusOnFirstInvalidControlAfterValidation
    )
}
```

### Dynamic Validation Result
`dynamicValidationResult` allows continuous monitoring of a validation state. For example, you can enable a button only when a form is valid.

Usage:
```kotlin
val dynamicResult = dynamicValidationResult(formValidator)
val submitButtonState = computed(dynamicResult) { result ->
    if (result.isValid) SubmitButtonState.Valid else SubmitButtonState.Invalid
}
```

### TextTransformation
`TextTransformation` provides a functional interface for applying text transformations, such as filtering.

Main method:
```kotlin
transform(text: String): String
```

Example:
```kotlin
val upperCaseTransformation = TextTransformation { text -> text.toUpperCase() }
val transformedText = upperCaseTransformation.transform("hello")
```

## Examples
Refer to [this example](https://github.com/MobileUpLLC/KMM-Form-Validation/tree/main/sample) for a demonstration of the library's functionality.

## License
Information about the license can be found [here](https://github.com/MobileUpLLC/KMM-Form-Validation/blob/master/LICENSE).
