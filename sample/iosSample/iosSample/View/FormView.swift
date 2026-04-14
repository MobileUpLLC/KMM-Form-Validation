import SwiftUI
import sharedSample
import ConfettiSwiftUI

struct FormView: View {
    
    let formComponent: FormComponent
    
    @ObservedObject var submitButtonState: UnsafeObservableState<SubmitButtonState>
    @ObservedObject var showConfetti: UnsafeObservableState<KotlinBoolean>
    @ObservedObject var selectedGender: UnsafeObservableState<Gender>
    
    @State private var triggerConfetti: Int = 0
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.showConfetti = UnsafeObservableState<KotlinBoolean>(formComponent.showConfetti)
        self.selectedGender = UnsafeObservableState<Gender>(formComponent.genderPicker.value)
    }
    
    var body: some View {
        ScrollView {
            VStack(spacing: 16) {
                TextFieldWithControl(
                    inputControl: formComponent.nameInput,
                    hint: L10n.nameHint
                )
                
                PickerView(
                    pickerControl: formComponent.genderPicker,
                    hint: L10n.genderHint,
                    displayText: { $0.localizedText }
                ) { closePicker in
                    ForEach(Gender.entries, id: \.self) { gender in
                        Button(action: {
                            formComponent.genderPicker.onValueChange(value: gender)
                            closePicker()
                        }) {
                            HStack {
                                Text(gender.localizedText)
                                    .frame(maxWidth: .infinity, alignment: .leading)
                                
                                if gender == selectedGender.value {
                                    Image(systemName: "checkmark")
                                        .foregroundColor(.green)
                                }
                            }
                            .padding(12)
                        }
                    }
                }
                
                TextFieldWithControl(
                    inputControl: formComponent.emailInput,
                    hint: L10n.emailHint
                )
                
                TextFieldWithControl(
                    inputControl: formComponent.phoneInput,
                    hint: L10n.phoneHint,
                    formatter: PhoneNumberFormatter()
                )
                
                SecureTextFieldWithControl(
                    inputControl: formComponent.passwordInput,
                    hint: L10n.passwordHint
                )
                
                SecureTextFieldWithControl(
                    inputControl: formComponent.confirmPasswordInput,
                    hint: L10n.confirmPasswordHint
                )
                
                ToggleView(
                    checkControl: formComponent.termsCheckBox,
                    label: L10n.termsHint
                )
                
                ToggleView(
                    checkControl: formComponent.newsletterCheckBox,
                    label: L10n.newsletterHint
                )
                
                SubmitButtonView(
                    buttonState: submitButtonState.value!,
                    label: L10n.submitButton
                ) {
                    formComponent.onSubmitClicked()
                    if showConfetti.value == true {
                        triggerConfetti += 1
                    }
                }
                
                Spacer()
            }
            .padding(16)
            .animation(.easeInOut(duration: 0.3))
            .confettiCannon(trigger: $triggerConfetti)
        }
    }
}


struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}
