import SwiftUI
import sharedSample

struct TextFieldWithControl: View {
    @ObservedObject private var text: UnsafeObservableState<NSString>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    @ObservedObject private var hasFocus: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var isEnabled: UnsafeObservableState<KotlinBoolean>
    
    @FocusState private var isFocused: Bool
    
    private let keyboardOptions: KeyboardOptions
    private let inputControl: InputControl
    private let hint: String
    private let isSecure: Bool
    
    init(inputControl: InputControl, hint: String, isSecure: Bool = false) {
        self.hint = hint
        self.inputControl = inputControl
        self.isSecure = isSecure
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = UnsafeObservableState(inputControl.text)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeObservableState(inputControl.hasFocus)
        self.isEnabled = UnsafeObservableState(inputControl.enabled)
    }
        
    var body: some View {
        VStack {
            TextFieldView(
                text: Binding {
                    String(text.value ?? "")
                } set: { value in
                    inputControl.onTextChanged(text: value)
                    text.reemitValue()
                },
                isSecure: isSecure,
                isError: error.value == nil,
                hint: hint
            )
            .disabled(!(isEnabled.value?.boolValue ?? false))
            .keyboardType(keyboardOptions.keyboardType.toUI())
            .submitLabel(keyboardOptions.imeAction.toUI())
            .textInputAutocapitalization(keyboardOptions.capitalization.toUI())
            .autocorrectionDisabled(!keyboardOptions.autoCorrect)
            .focused($isFocused)
            .onChange(of: isFocused) { newValue in
                inputControl.onFocusChanged(hasFocus: newValue)
            }
            .onChange(of: hasFocus.value?.boolValue ?? false) { newValue in
                isFocused = newValue
            }
            
            if let error = error.value {
                HStack {
                    Text(error.localized())
                        .foregroundColor(.red)
                    Spacer()
                }
            }
        }
        .padding(.vertical, 10)
    }
    
    private struct TextFieldView: View {
        @Binding var text: String
        
        let isSecure: Bool
        let isError: Bool
        let hint: String
        
        var body: some View {
            createTextField()
                .padding(10)
                .overlay {
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(isError ? Color.gray : Color.red, lineWidth: 2)
                }
        }
        
        private func createTextField() -> some View {
            if isSecure {
                SecureField(
                    text: $text,
                    prompt: Text(hint),
                    label: {
                        Text("")
                    }
                )
            } else {
                TextField(hint, text: $text)
            }
        }
    }
}
