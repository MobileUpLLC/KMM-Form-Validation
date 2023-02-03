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
        self.text = UnsafeObservableState(inputControl.text)
        self.error = UnsafeObservableState(inputControl.error)
        self.hasFocus = UnsafeMutableObservableState(inputControl.hasFocus)
        self.enabled = UnsafeMutableObservableState(inputControl.enabled)
    }
    
    private let hint: String
    
    private let inputControl: InputControl

    @ObservedObject
    private var text: UnsafeObservableState<NSString>
    
    @ObservedObject
    private var error: UnsafeObservableState<StringDesc>
    
    @ObservedObject
    private var hasFocus: UnsafeMutableObservableState<KotlinBoolean>
    
    @ObservedObject
    private var enabled: UnsafeMutableObservableState<KotlinBoolean>

    @State
    private var keyboardOptions: KeyboardOptions
    
    @FocusState
    private var isFocused: Bool
        
    var body: some View {
        VStack {
            TextField(hint, value: Binding {
                String(text.value ?? "")
            } set: { value in
                inputControl.onTextChanged(text:value)
            }, formatter: VisualFormatter(inputControl.visualTransformation))
                .textFieldStyle(.roundedBorder)
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
