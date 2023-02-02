//
//  SubmitButtonView.swift
//  iosSample
//
//  Created by Andrey on 01.02.2023.
//

import SwiftUI
import sharedSample

struct SubmitButtonView: View {
    
    init(formComponent: FormComponent, label: String) {
        self.formComponent = formComponent
        self.label = label
        self.buttonState = ObservableFlow(formComponent.submitButtonState)
    }
    
    let formComponent: FormComponent
    
    @ObservedObject
    private var buttonState: ObservableFlow<SubmitButtonState>
    
    let label: String
    
    var body: some View {
        let color = buttonState.value!.color.toUIColor()
        Button(action: {
            formComponent.onSubmitClicked()
        }, label: {
            Text(label)
                .padding(8)
        })
        .buttonStyle(BorderedButtonStyle())
        .foregroundColor(Color(color))
    }
}
