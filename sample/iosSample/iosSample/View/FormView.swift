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
    
    let formComponent: FormComponent

    @ObservedObject private var submitButtonState: UnsafeObservableState<SubmitButtonState>
    @ObservedObject private var valid: UnsafeObservableState<KotlinBoolean>
    
    @FocusState private var focus: Int?
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        self.submitButtonState = UnsafeObservableState<SubmitButtonState>(formComponent.submitButtonState)
        self.valid = UnsafeObservableState<KotlinBoolean>(formComponent.valid)
    }
    
    var body: some View {
        ScrollView(showsIndicators: false) {
            ScrollViewReader { proxy in
                VStack{
                    TextFieldWithControl(
                        inputControl: formComponent.nameInput,
                        hint: MR.strings().name_hint.desc().localized(),
                        isSecure: false
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
                        hint: MR.strings().email_hint.desc().localized(),
                        isSecure: false
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
                        hint: MR.strings().phone_hint.desc().localized(),
                        isSecure: false
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
                    
                    if(valid.value?.boolValue ?? false) {
                        Text(MR.strings().success_message.desc().localized())
                            .padding(8)
                    }
                    
                    Color.clear.padding(.bottom, 70)
                }
            }
        }
    }
}


struct FormView_Previews: PreviewProvider {
    static var previews: some View {
        FormView(formComponent: FakeFormComponent())
    }
}

private struct ScrolledFocusField: ViewModifier {
    
    @FocusState var focus: Int?
    
    let id: Int
    let nextId: Int
    let proxy: ScrollViewProxy
    let anchor: UnitPoint
    let nextAnchor: UnitPoint
    
    func body(content: Content) -> some View {
        content
            .focused($focus, equals: id)
            .onTapGesture {
                scrollToRowWithAnimation(proxy: proxy, id, anchor: anchor)
            }
            .onSubmit {
                scrollToRowWithAnimation(proxy: proxy, nextId, anchor: nextAnchor)
                focus = nextId
            }
    }
}

private extension ViewModifier {
    
    func scrollToRowWithAnimation(proxy: ScrollViewProxy, _ row: Int, anchor: UnitPoint = .center) {
        withAnimation {
            proxy.scrollTo(row, anchor: anchor)
        }
    }
}

extension View {
    
    func scrolledFocus(
        focus: FocusState<Int?>,
        id: Int,
        nextId: Int,
        proxy: ScrollViewProxy,
        anchor: UnitPoint = .center,
        nextAnchor: UnitPoint = .center
    ) -> some View {
        modifier(ScrolledFocusField(
            focus: focus,
            id: id,
            nextId: nextId,
            proxy: proxy,
            anchor: anchor,
            nextAnchor: nextAnchor
        ))
    }
}
