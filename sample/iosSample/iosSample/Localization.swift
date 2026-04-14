import Foundation
import sharedSample

enum L10n {
    static let nameHint = NSLocalizedString("name_hint", comment: "")
    static let emailHint = NSLocalizedString("email_hint", comment: "")
    static let phoneHint = NSLocalizedString("phone_hint", comment: "")
    static let passwordHint = NSLocalizedString("password_hint", comment: "")
    static let genderHint = NSLocalizedString("gender_hint", comment: "")
    static let confirmPasswordHint = NSLocalizedString("confirm_password_hint", comment: "")
    static let termsHint = NSLocalizedString("terms_hint", comment: "")
    static let newsletterHint = NSLocalizedString("newsletter_hint", comment: "")
    static let submitButton = NSLocalizedString("submit_button", comment: "")
}

extension SampleValidationError {
    var localizedText: String {
        if self == SampleValidationError.blankfield {
            return NSLocalizedString("field_is_blank_error_message", comment: "")
        }
        if self == SampleValidationError.invalidemail {
            return NSLocalizedString("invalid_email_error_message", comment: "")
        }
        if self == SampleValidationError.invalidphone {
            return NSLocalizedString("invalid_phone_error_message", comment: "")
        }
        if self == SampleValidationError.invalidpassword {
            return NSLocalizedString("invalid_password_error_message", comment: "")
        }
        if self == SampleValidationError.passwordsdonotmatch {
            return NSLocalizedString("passwords_do_not_match_error_message", comment: "")
        }
        if self == SampleValidationError.termsnotaccepted {
            return NSLocalizedString("terms_are_accepted_error_message", comment: "")
        }

        fatalError("Unsupported validation error: \(self)")
    }
}

extension Gender {
    var localizedText: String {
        if self == Gender.male {
            return NSLocalizedString("gender_male", comment: "")
        }
        if self == Gender.female {
            return NSLocalizedString("gender_female", comment: "")
        }
        if self == Gender.other {
            return NSLocalizedString("gender_other", comment: "")
        }

        fatalError("Unsupported gender: \(self)")
    }
}
