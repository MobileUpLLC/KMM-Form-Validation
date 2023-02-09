import sharedSample
import SwiftUI

extension KeyboardType {
    func toUI() -> UIKeyboardType {
        switch self {
        case .ascii:
            return .asciiCapable
        case .number:
            return .numberPad
        case .phone:
            return .phonePad
        case .uri:
            return .URL
        case .email:
            return .emailAddress
        case .numberpassword:
            return .numberPad
        case .text, .password:
            return .default
        default:
            return .default
        }
    }
}

extension ImeAction {
    func toUI() -> SubmitLabel {
        switch self {
        case .done:
            return .done
        case .go:
            return .go
        case .search:
            return .search
        case .send:
            return .send
        case .previous:
            return .route
        case .next:
            return .next
        default:
            return .done
        }
    }
}

extension KeyboardCapitalization {
    func toUI() -> TextInputAutocapitalization {
        switch self {
        case .characters:
            return .characters
        case .sentences:
            return .sentences
        case .words:
            return .words
        case .none:
            return .never
        default:
            return .never
        }
    }
}

extension SubmitButtonState {
    func toUI() -> Color {
        switch self {
        case SubmitButtonState.invalid:
            return .red
        case SubmitButtonState.valid:
            return .green
        default:
            return .red
        }
    }
}
