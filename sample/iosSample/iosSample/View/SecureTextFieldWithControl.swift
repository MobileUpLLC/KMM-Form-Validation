//
//  SecureTextFieldWithControl.swift
//  iosSample
//
//  Created by Andrey on 01.02.2023.
//

import SwiftUI
import Combine
import sharedSample

struct SecureTextFieldWithControl: View {
    
    init(inputControl: InputControl, hint: String) {
        self.hint = hint
        self.inputControl = inputControl
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = ObservableFlow(inputControl.text)
        self.error = ObservableFlow(inputControl.error)
        self.hasFocus = MutableObservableFlow(inputControl.hasFocus)
        self.enabled = MutableObservableFlow(inputControl.enabled)
    }
    
    private let hint: String
    
    private let inputControl: InputControl

    @ObservedObject
    private var text: ObservableFlow<NSString>
    
    @ObservedObject
    private var error: ObservableFlow<StringDesc>
    
    @ObservedObject
    private var hasFocus: MutableObservableFlow<KotlinBoolean>
    
    @ObservedObject
    private var enabled: MutableObservableFlow<KotlinBoolean>

    @State
    private var keyboardOptions: KeyboardOptions
    
    @FocusState
    private var isFocused: Bool
        
    var body: some View {
        VStack {
            SecureField(text: Binding {
                String(text.value ?? "")
            } set: { value in
                inputControl.onTextChanged(text:value)
            }, prompt: Text(hint), label: {
                Text("123")
            })  .textFieldStyle(.roundedBorder)
                .focused($isFocused)
                .onChange(of: isFocused) { newValue in
                    hasFocus.setValue(value: KotlinBoolean(value: newValue))
                }
                .disabled(!Bool(enabled.value ?? true))
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
