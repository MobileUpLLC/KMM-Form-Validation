import SwiftUI
import Combine
import sharedSample

struct TextFieldWithControl: View {
    
    private let hint: String
    private let inputControl: InputControl
    private let keyboardOptions: KeyboardOptions
    private let visualTransformation: VisualTransformation

    @ObservedObject
    private var text: UnsafeObservableState<NSString>
    
    @ObservedObject
    private var error: UnsafeObservableState<StringDesc>
    
    @ObservedObject
    private var hasFocus: UnsafeObservableState<KotlinBoolean>
    
    @ObservedObject
    private var enabled: UnsafeObservableState<KotlinBoolean>
    
    @FocusState
    private var isFocused: Bool
    
    init(inputControl: InputControl, hint: String) {
        self.hint = hint
        self.inputControl = inputControl
        self.keyboardOptions = inputControl.keyboardOptions
        self.visualTransformation = inputControl.visualTransformation
        self.text = UnsafeObservableState(inputControl.text)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeObservableState(inputControl.hasFocus)
        self.enabled = UnsafeObservableState(inputControl.enabled)
    }
        
    var body: some View {
        VStack {
            TextField(
                hint,
                text: Binding {
                    visualTransformation.filter(text: String(text.value ?? "")).text
                } set: { value in
                    inputControl.onTextChanged(text: visualTransformation.restore(text: value))
                }
            )
            .textFieldStyle(.roundedBorder)
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
                    .foregroundColor(.red)
            }
        }
        .padding(4)
    }
}
