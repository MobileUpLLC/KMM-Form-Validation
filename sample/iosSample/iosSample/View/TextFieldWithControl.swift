import SwiftUI
import sharedSample

struct TextFieldWithControl: View {
    @ObservedObject private var text: UnsafeObservableState<NSString>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    @ObservedObject private var hasFocus: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var enabled: UnsafeObservableState<KotlinBoolean>
    
    @State private var keyboardOptions: KeyboardOptions
    @FocusState private var isFocused: Bool
    
    private let hint: String
    private let inputControl: InputControl
    private let isSecure: Bool
    
    init(inputControl: InputControl, hint: String, isSecure: Bool) {
        self.hint = hint
        self.inputControl = inputControl
        self.isSecure = isSecure
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = UnsafeObservableState(inputControl.text)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeObservableState(inputControl.hasFocus)
        self.enabled = UnsafeObservableState(inputControl.enabled)
    }
        
    var body: some View {
        VStack {
            TextFieldView(
                text: Binding {
                    String(text.value ?? "")
                } set: { value in
                    inputControl.onTextChanged(text: value)
                },
                isSecure: isSecure,
                hint: hint,
                formatter: VisualFormatter(inputControl.visualTransformation)
            )
            .disabled(!(enabled.value?.boolValue ?? false))
            .keyboardType(keyboardOptions.keyboardType.toUI())
            .submitLabel(keyboardOptions.imeAction.toUI())
            .textInputAutocapitalization(keyboardOptions.capitalization.toUI())
            .autocorrectionDisabled(!keyboardOptions.autoCorrect)
            .focused($isFocused)
            .onChange(of: isFocused) { newValue in
                inputControl.onFocusChanged(hasFocus: newValue)
            }
            
            if let error = error.value {
                Text(error.localized())
                    .foregroundColor(.red)
            }
        }
        .padding(4)
    }
    
    private struct TextFieldView: View {
        @Binding var text: String
        
        let isSecure: Bool
        let hint: String
        let formatter: Formatter
        
        var body: some View {
            if isSecure {
                SecureField(
                    text: $text,
                    prompt: Text(hint),
                    label: {
                        Text("")
                    }
                )
                .textFieldStyle(.roundedBorder)
            } else {
                TextField(
                    hint,
                    value: $text,
                    formatter: formatter
                )
                .textFieldStyle(.roundedBorder)
            }
        }
    }
}
