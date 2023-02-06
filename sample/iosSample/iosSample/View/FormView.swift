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
            TextFieldWithControl(inputControl: formComponent.nameInput, hint: "Name")
            TextFieldWithControl(inputControl: formComponent.emailInput, hint: "Email")
            TextFieldWithControl(inputControl: formComponent.phoneInput, hint: "Phone")
            SecureTextFieldWithControl(inputControl: formComponent.passwordInput, hint: "Password")
            SecureTextFieldWithControl(inputControl: formComponent.confirmPasswordInput, hint: "Confirm Password")
            ToggleView(checkControl: formComponent.termsCheckBox, label: "Terms")
            SubmitButtonView(buttonState: submitButtonState.value!, label: "Submit", action: formComponent.onSubmitClicked)
            
            if(valid.value?.boolValue ?? false) {
                Text("Success").padding(8)
            }
        }
    }
}


struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}
