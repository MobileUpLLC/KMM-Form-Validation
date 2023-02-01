//
//  ContentView.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import SwiftUI
import Combine
import sharedSample

struct TextFieldWithControl: View {
    
    init(inputControl: InputControl, hint: String) {
        self.hint = hint
        self.inputControl = inputControl
        self.keyboardOptions = inputControl.keyboardOptions
        self.text = ObservableState(inputControl.text)
        self.error = ObservableState(inputControl.error)
        self.hasFocus = MutableObservableState(inputControl.hasFocus)
        self.enabled = MutableObservableState(inputControl.enabled)
    }
    
    private let hint: String
    
    private let inputControl: InputControl

    @ObservedObject
    private var text: ObservableState<NSString>
    
    @ObservedObject
    private var error: ObservableState<Optional<StringDesc>>
    
    @ObservedObject
    private var hasFocus: MutableObservableState<KotlinBoolean>
    
    @ObservedObject
    private var enabled: MutableObservableState<KotlinBoolean>

    @State
    private var keyboardOptions: KeyboardOptions
    
    @FocusState
    private var isFocused: Bool
        
    var body: some View {
        VStack {
            TextField(hint, value: Binding {
                String(text.value)
            } set: { value in
                inputControl.onTextChanged(text:value)
            }, formatter: VisualFormatter(inputControl.visualTransformation))
                .textFieldStyle(.roundedBorder)
                .focused($isFocused)
                .onChange(of: isFocused) { newValue in
                    hasFocus.setValue(value: KotlinBoolean(value: newValue))
                }
                .disabled(!Bool(enabled.value))
                .keyboardType(keyboardOptions.keyboardType.toUI())
                .submitLabel(keyboardOptions.imeAction.toUI())
                .textInputAutocapitalization(keyboardOptions.capitalization.toUI())
                .autocorrectionDisabled(!keyboardOptions.autoCorrect)
            
            let errorMessage = error.value.get()
            if let error = errorMessage {
                Text(error.localized())
                    .foregroundColor(.red)
            }
        }
        .padding(4)
    }
}
