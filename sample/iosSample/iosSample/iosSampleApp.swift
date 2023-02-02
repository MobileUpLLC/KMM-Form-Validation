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
        successFlow = ObservableFlow(flow: formComponent.dropKonfettiEvent)
    }
    
    let formComponent: FormComponent
    let successFlow: ObservableFlow<KotlinUnit>
    
    @State
    var isSuccessShow: Bool = false
    
    var body: some Scene {
        WindowGroup {
            VStack{
                if isSuccessShow{
                    Text("Success")
                        .padding(8)
                }
                TextFieldWithControl(inputControl: formComponent.nameInput, hint: "Name")
                TextFieldWithControl(inputControl: formComponent.phoneInput, hint: "Phone")
                TextFieldWithControl(inputControl: formComponent.emailInput, hint: "Email")
                SecureTextFieldWithControl(inputControl: formComponent.passwordInput, hint: "Password")
                SecureTextFieldWithControl(inputControl: formComponent.confirmPasswordInput, hint: "Confirm Password")
                ToggleView(checkControl: formComponent.termsCheckBox, label: "Terms")
                SubmitButtonView(formComponent: formComponent, label: "Submit")
                    .onReceive(successFlow.$value) { output in
                        if output != nil{
                            isSuccessShow = true
                        }
                    }
            }
        }
    }
}
