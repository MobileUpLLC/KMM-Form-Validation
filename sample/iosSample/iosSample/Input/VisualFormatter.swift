import Foundation

final class PhoneNumberFormatter: Formatter {
    private let firstHardcodeSlot = "+7"
    private let secondHardcodeSlot = " ("
    private let thirdHardcodeSlot = ") "
    private let decorateHardcodeSlot = "-"

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init() {
        super.init()
    }

    override func string(for obj: Any?) -> String? {
        guard let string = obj as? String else {
            return nil
        }

        let trimmed = String(string.prefix(10))
        var output = firstHardcodeSlot

        if !trimmed.isEmpty {
            output += secondHardcodeSlot
        }

        for (index, char) in trimmed.enumerated() {
            output.append(char)
            switch index {
            case 2:
                output += thirdHardcodeSlot
            case 5, 7:
                output += decorateHardcodeSlot
            default:
                break
            }
        }

        return output
    }

    override func getObjectValue(_ obj: AutoreleasingUnsafeMutablePointer<AnyObject?>?, for string: String, errorDescription error: AutoreleasingUnsafeMutablePointer<NSString?>?) -> Bool {
        let result = string
            .replacingOccurrences(of: firstHardcodeSlot, with: "")
            .replacingOccurrences(of: secondHardcodeSlot, with: "")
            .replacingOccurrences(of: thirdHardcodeSlot, with: "")
            .replacingOccurrences(of: decorateHardcodeSlot, with: "")
        obj?.pointee = result as AnyObject
        return true
    }
}
