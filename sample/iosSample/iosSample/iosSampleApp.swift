//
//  iosSampleApp.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import SwiftUI
import sharedSample

@main
struct iosSampleApp: App {
    
    init() {
        formComponent = Application.shared.getFormComponent()
    }
    
    let formComponent: FormComponent
    
    var body: some Scene {
        WindowGroup {
            VStack{
                TextFieldWithControl(inputControl: formComponent.nameInput, hint: "Name")
                TextFieldWithControl(inputControl: formComponent.phoneInput, hint: "Phone")
                TextFieldWithControl(inputControl: formComponent.emailInput, hint: "Email")
                SecureTextFieldWithControl(inputControl: formComponent.passwordInput, hint: "Password")
                SecureTextFieldWithControl(inputControl: formComponent.confirmPasswordInput, hint: "Confirm Password")
                ToggleView(checkControl: formComponent.termsCheckBox, label: "Terms")
                SubmitButtonView(formComponent: formComponent, label: "Submit")
            }
        }
    }
}
