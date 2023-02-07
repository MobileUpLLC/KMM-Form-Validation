import Foundation
import sharedSample
import SwiftUI

extension KeyboardType {
    func toUI() -> UIKeyboardType {
        let keyboardType: UIKeyboardType
        switch self {
            case .text: keyboardType = .default
            case .ascii: keyboardType = .asciiCapable
            case .number: keyboardType = .numberPad
            case .phone: keyboardType = .phonePad
            case .uri: keyboardType = .URL
            case .email: keyboardType = .emailAddress
            case .password: keyboardType = .default
            case .numberpassword: keyboardType = .numberPad
            default: keyboardType = .default
        }
        return keyboardType
    }
}

extension ImeAction {
    func toUI() -> SubmitLabel {
        let label: SubmitLabel
        switch self {
            case .default_: label = .done
            case .done: label = .done
            case .go: label = .go
            case .search: label = .search
            case .send: label = .send
            case .previous: label = .route
            case .next: label = .next
            default: label = .done
        }
        return label
    }
}

extension KeyboardCapitalization {
    func toUI() -> TextInputAutocapitalization{
        let capitalization: TextInputAutocapitalization
        switch self {
            case .none: capitalization = .never
            case .characters: capitalization = .characters
            case .sentences: capitalization = .sentences
            case .words: capitalization = .words
            default: capitalization = .never
        }
        return capitalization
    }
}

extension SubmitButtonState {
    func toUI() -> Color {
        let color: Color
        switch self {
        case SubmitButtonState.invalid: color = Color.red
        case SubmitButtonState.valid: color = Color.green
        default: color = Color.red
        }
        return color
    }
}
