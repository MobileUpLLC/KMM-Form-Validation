import SwiftUI
import sharedSample

struct SubmitButton: View {
    let label: String
    let buttonState: SubmitButtonState
    let action: () -> Void
    
    var body: some View {
        Button(
            action: action,
            label: {
                Text(label)
                    .padding(8)
            }
        )
        .buttonStyle(BorderedButtonStyle())
        .foregroundColor(buttonState.toUI())
    }
}
