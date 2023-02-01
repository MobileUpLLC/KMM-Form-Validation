//
//  ToggleView.swift
//  iosSample
//
//  Created by Andrey on 01.02.2023.
//

import SwiftUI
import sharedSample

struct ToggleView: View {
    
    init(checkControl: CheckControl, label: String) {
        self.label = label
        termsControl = checkControl
        termsState = ObservableState(checkControl.checked)
        error = ObservableState(checkControl.error)
    }
    
    var termsControl: CheckControl
    
    @ObservedObject
    var termsState: ObservableState<KotlinBoolean>
    
    @ObservedObject
    private var error: ObservableState<Optional<StringDesc>>
    
    let label: String
    
    var body: some View {
        VStack {
            Toggle(isOn: Binding(
                get: { Bool(termsState.value) },
                set: termsControl.onCheckedChanged
            ),
                label: {
                Text(label)
                }
            )
            let errorMessage = error.value.get()
            if let error = errorMessage {
                Text(error.localized())
                    .foregroundColor(.red)
            }
        }
        .padding(4)
    }
}
