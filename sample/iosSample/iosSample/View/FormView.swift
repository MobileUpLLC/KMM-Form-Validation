import SwiftUI
import sharedSample

struct FormView: View {

    let formComponent: FormComponent
    
    @ObservedObject
    var submitButtonState: UnsafeObservableState<SubmitButtonState>
    
    @ObservedObject
    var valid: UnsafeObservableState<KotlinBoolean>
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.valid = UnsafeObservableState<KotlinBoolean>(formComponent.valid)
    }
    
    var body: some View {
        VStack{
            TextFieldWithControl(
                inputControl: formComponent.nameInput,
                hint: MR.strings().name_hint.desc().localized()
            )
            
            TextFieldWithControl(
                inputControl: formComponent.emailInput,
                hint: MR.strings().email_hint.desc().localized()
            )
            
            TextFieldWithControl(
                inputControl: formComponent.phoneInput,
                hint: MR.strings().phone_hint.desc().localized()
            )
            
            SecureTextFieldWithControl(
                inputControl: formComponent.passwordInput,
                hint: MR.strings().password_hint.desc().localized()
            )
            
            SecureTextFieldWithControl(
                inputControl: formComponent.confirmPasswordInput,
                hint: MR.strings().confirm_password_hint.desc().localized()
            )
            
            ToggleView(
                checkControl: formComponent.termsCheckBox,
                label: MR.strings().terms_hint.desc().localized()
            )
            
            SubmitButtonView(
                buttonState: submitButtonState.value!,
                label: MR.strings().submit_button.desc().localized(),
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
