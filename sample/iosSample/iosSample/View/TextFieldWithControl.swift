import SwiftUI
import Combine
import sharedSample

struct TextFieldWithControl: View {
    
    private let hint: String
    private let inputControl: InputControl
    private let formatter: Formatter?
    
    @ObservedObject private var text: UnsafeObservableState<NSString>
    @ObservedObject private var error: UnsafeObservableState<SampleValidationError>
    @ObservedObject private var hasFocus: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var enabled: UnsafeObservableState<KotlinBoolean>
    
    @State private var keyboardOptions: KeyboardOptions
    @FocusState private var isFocused: Bool
    
    init(inputControl: InputControl, hint: String, formatter: Formatter? = nil) {
        self.hint = hint
        self.inputControl = inputControl
        self.formatter = formatter
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = UnsafeObservableState(inputControl.value)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeObservableState(inputControl.hasFocus)
        self.enabled = UnsafeObservableState(inputControl.enabled)
    }
    
    var body: some View {
        VStack {
            field
                .textFieldStyle(.roundedBorder)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(error.value != nil ? Color.red : Color.clear, lineWidth: 1)
                )
                .focused($isFocused)
                .onChange(of: isFocused) { newValue in
                    inputControl.onFocusChange(hasFocus: newValue)
                }
                .disabled(!(enabled.value?.boolValue ?? false))
                .keyboardType(keyboardOptions.keyboardType.toUI())
                .submitLabel(keyboardOptions.imeAction.toUI())
                .textInputAutocapitalization(keyboardOptions.capitalization.toUI())
                .autocorrectionDisabled(!keyboardOptions.autoCorrect)
            
            if let error = error.value {
                Text(error.localizedText)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .font(.caption)
                    .foregroundColor(.red)
            }
        }
        .animation(Animation.easeInOut(duration: 0.3), value: UUID())
    }

    private var textBinding: Binding<String> {
        Binding {
            String(text.value ?? "")
        } set: { value in
            inputControl.onValueChange(value: value)
        }
    }

    @ViewBuilder
    private var field: some View {
        if let formatter {
            TextField(
                hint,
                value: textBinding,
                formatter: formatter
            )
        } else {
            TextField(
                hint,
                text: textBinding
            )
        }
    }
}
