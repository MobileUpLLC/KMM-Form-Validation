import SwiftUI
import sharedSample

struct FormView: View {
    @ObservedObject var submitButtonState: UnsafeObservableState<SubmitButtonState>
    @ObservedObject var valid: UnsafeObservableState<KotlinBoolean>

    let formComponent: FormComponent
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.valid = UnsafeObservableState<KotlinBoolean>(formComponent.valid)
    }
    
    var body: some View {
        VStack{
            TextFieldWithControl(
                inputControl: formComponent.nameInput,
                hint: MR.strings().name_hint.desc().localized(),
                isSecure: false
            )
            
            TextFieldWithControl(
                inputControl: formComponent.emailInput,
                hint: MR.strings().email_hint.desc().localized(),
                isSecure: false
            )
            
            TextFieldWithControl(
                inputControl: formComponent.phoneInput,
                hint: MR.strings().phone_hint.desc().localized(),
                isSecure: false
            )
            
            TextFieldWithControl(
                inputControl: formComponent.passwordInput,
                hint: MR.strings().password_hint.desc().localized(),
                isSecure: true
            )
            
            TextFieldWithControl(
                inputControl: formComponent.confirmPasswordInput,
                hint: MR.strings().confirm_password_hint.desc().localized(),
                isSecure: true
            )
            
            ToggleView(
                checkControl: formComponent.termsCheckBox,
                label: MR.strings().terms_hint.desc().localized()
            )
            
            SubmitButton(
                label: MR.strings().submit_button.desc().localized(),
                buttonState: submitButtonState.value!,
                action: formComponent.onSubmitClicked
            )
            
            if(valid.value?.boolValue ?? false) {
                Text(MR.strings().success_message.desc().localized())
                    .padding(8)
            }
        }
    }
}


struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}
