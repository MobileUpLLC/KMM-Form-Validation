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
                    .padding(.vertical, 8)
                    .padding(.horizontal, 20)
            }
        )
        .buttonStyle(BorderedButtonStyle())
        .foregroundColor(buttonState.toUI())
        .cornerRadius(20)
    }
}
