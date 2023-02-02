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
        termsState = ObservableFlow(checkControl.checked)
        error = ObservableFlow(checkControl.error)
    }
    
    var termsControl: CheckControl
    
    @ObservedObject
    var termsState: ObservableFlow<KotlinBoolean>
    
    @ObservedObject
    private var error: ObservableFlow<StringDesc>
    
    let label: String
    
    var body: some View {
        VStack {
            Toggle(isOn: Binding(
                get: { Bool(termsState.value ?? false) },
                set: termsControl.onCheckedChanged
            ),
                label: {
                Text(label)
                }
            )
            if let error = error.value {
                Text(error.localized())
                    .foregroundColor(.red)
            }
        }
        .padding(4)
    }
}
