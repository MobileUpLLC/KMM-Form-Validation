import SwiftUI
import sharedSample
import ConfettiSwiftUI

struct FormView: View {
    
    let formComponent: FormComponent
    
    @ObservedObject var submitButtonState: UnsafeObservableState<SubmitButtonState>
    @ObservedObject var showConfetti: UnsafeObservableState<KotlinBoolean>
    @State private var triggerConfetti: Int = 0
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.showConfetti = UnsafeObservableState<KotlinBoolean>(formComponent.showConfetti)
    }
    
    var body: some View {
        VStack(spacing: 16) {
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
            
            ToggleView(
                checkControl: formComponent.newsletterCheckBox,
                label: MR.strings().newsletter_hint.desc().localized()
            )
            
            SubmitButtonView(
                buttonState: submitButtonState.value!,
                label: MR.strings().submit_button.desc().localized()
            ) {
                formComponent.onSubmitClicked()
                if showConfetti.value == true {
                    triggerConfetti += 1
                }
            }
            
            Spacer()
        }
        .padding(16)
        .confettiCannon(trigger: $triggerConfetti)
    }
}


struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}
