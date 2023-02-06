import SwiftUI
import sharedSample

struct SubmitButtonView: View {
    
    let buttonState: SubmitButtonState
    let label: String
    let action: () -> Void
    
    init(buttonState: SubmitButtonState, label: String, action: @escaping () -> Void) {
        self.label = label
        self.buttonState = buttonState
        self.action = action
    }
    
    var body: some View {
        Button(
            action: action,
            label: {
                Text(label).padding(8)
            }
        )
        .buttonStyle(BorderedButtonStyle())
        .foregroundColor(buttonState.toUI())
    }
}
