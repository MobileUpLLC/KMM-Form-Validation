import SwiftUI
import Combine
import sharedSample

struct SecureTextFieldWithControl: View {
    
    private let hint: String
    private let inputControl: InputControl
    
    @ObservedObject private var text: UnsafeObservableState<NSString>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    @ObservedObject private var hasFocus: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var enabled: UnsafeObservableState<KotlinBoolean>
    
    @State private var keyboardOptions: KeyboardOptions
    @FocusState private var isFocused: Bool
    
    init(inputControl: InputControl, hint: String) {
        self.hint = hint
        self.inputControl = inputControl
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = UnsafeObservableState(inputControl.valueState)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeObservableState(inputControl.hasFocus)
        self.enabled = UnsafeObservableState(inputControl.enabled)
    }
    
    var body: some View {
        VStack {
            SecureField(
                text: Binding {
                    String(text.value ?? "")
                } set: { value in
                    inputControl.onValueChanged(value: value)
                },
                prompt: Text(hint),
                label: {
                    Text("Password input")
                }
            )
            .textContentType(.password)
            .focused($isFocused)
            .textFieldStyle(.roundedBorder)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(error.value != nil ? Color.red : Color.clear, lineWidth: 1)
            )
            .focused($isFocused)
            .onChange(of: isFocused) { newValue in
                inputControl.onFocusChanged(hasFocus: newValue)
            }
            .disabled(!(enabled.value?.boolValue ?? false))
            .keyboardType(keyboardOptions.keyboardType.toUI())
            .submitLabel(keyboardOptions.imeAction.toUI())
            .textInputAutocapitalization(keyboardOptions.capitalization.toUI())
            .autocorrectionDisabled(!keyboardOptions.autoCorrect)
            
            if let error = error.value {
                Text(error.localized())
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .font(.caption)
                    .foregroundColor(.red)
            }
        }
        .animation(Animation.easeInOut(duration: 0.3), value: UUID())
    }
}
