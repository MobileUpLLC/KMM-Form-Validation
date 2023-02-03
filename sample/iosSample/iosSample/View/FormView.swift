//
//  FormView.swift
//  iosSample
//
//  Created by Andrey on 03.02.2023.
//

import SwiftUI
import sharedSample

struct FormView: View {
    
    init(formComponent: FormComponent) {
        self.formComponent = formComponent
        successFlow = ObservableFlow<KotlinUnit>(flow: formComponent.dropKonfettiEvent)
    }
    
    let formComponent: FormComponent
    let successFlow: ObservableFlow<KotlinUnit>
    
    @State
    var isSuccessShow: Bool = false
    
    var body: some View {
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


