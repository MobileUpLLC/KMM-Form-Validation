import SwiftUI
import sharedSample

struct FormView: View {
    private enum Field: Int {
        case name = 0
        case email = 1
        case phone = 2
        case password = 3
        case passwordConfirmation = 4
    }
    
    @ObservedObject private var submitButtonState: UnsafeObservableState<SubmitButtonState>
    @ObservedObject private var isValid: UnsafeObservableState<KotlinBoolean>
    
    @FocusState private var focus: Int?
    
    private let formComponent: FormComponent
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.isValid = UnsafeObservableState<KotlinBoolean>(formComponent.valid)
    }
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            ScrollViewReader { proxy in
                VStack {
                    getTitleView()
                    getTextFiledStackView(proxy: proxy)
                    ToggleView(
                        checkControl: formComponent.termsCheckBox,
                        label: MR.strings().terms_hint.desc().localized()
                    )
                    .id(5)
                    SubmitButton(
                        label: MR.strings().submit_button.desc().localized(),
                        buttonState: submitButtonState.value!,
                        action: formComponent.onSubmitClicked
                    )
                    
                    if(isValid.value?.boolValue ?? false) {
                        Text(MR.strings().success_message.desc().localized())
                            .padding(8)
                            .foregroundColor(submitButtonState.value?.toUI())
                    }
                    
                    Color.clear.padding(.bottom, 70)
                }
                .padding()
            }
        }
    }
    
    private func getTitleView() -> some View {
        HStack {
            Text("Default Form")
                .font(.largeTitle)
                .fontWeight(.bold)
                .foregroundColor(.gray)
            Spacer()
        }
        .padding(.vertical, 20)
    }
    
    private func getTextFiledStackView(proxy: ScrollViewProxy) -> some View {
        VStack {
            TextFieldWithControl(
                inputControl: formComponent.nameInput,
                hint: MR.strings().name_hint.desc().localized()
            )
            .scrolledFocus(
                focus: _focus,
                id: Field.name.rawValue,
                nextId: Field.email.rawValue,
                proxy: proxy
            )
            .id(Field.name.rawValue)
            TextFieldWithControl(
                inputControl: formComponent.emailInput,
                hint: MR.strings().email_hint.desc().localized()
            )
            .scrolledFocus(
                focus: _focus,
                id: Field.email.rawValue,
                nextId: Field.phone.rawValue,
                proxy: proxy
            )
            .id(Field.email.rawValue)
            TextFieldWithControl(
                inputControl: formComponent.phoneInput,
                hint: MR.strings().phone_hint.desc().localized()
            )
            .scrolledFocus(
                focus: _focus,
                id: Field.phone.rawValue,
                nextId: Field.password.rawValue,
                proxy: proxy
            )
            .id(Field.phone.rawValue)
            TextFieldWithControl(
                inputControl: formComponent.passwordInput,
                hint: MR.strings().password_hint.desc().localized(),
                isSecure: true
            )
            .scrolledFocus(
                focus: _focus,
                id: Field.password.rawValue,
                nextId: Field.passwordConfirmation.rawValue,
                proxy: proxy
            )
            .id(Field.password.rawValue)
            TextFieldWithControl(
                inputControl: formComponent.confirmPasswordInput,
                hint: MR.strings().confirm_password_hint.desc().localized(),
                isSecure: true
            )
            .scrolledFocus(
                focus: _focus,
                id: Field.passwordConfirmation.rawValue,
                nextId: 5,
                proxy: proxy
            )
            .id(Field.passwordConfirmation.rawValue)
        }
    }
}

struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}
