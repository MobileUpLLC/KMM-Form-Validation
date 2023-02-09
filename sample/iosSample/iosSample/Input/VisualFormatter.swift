import Foundation
import sharedSample

class VisualFormatter: Formatter {
    let visualTransformation: VisualTransformation
    
    init(_ visualTransformation: VisualTransformation) {
        self.visualTransformation = visualTransformation
        
        super.init()
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func string(for obj: Any?) -> String? {
        let string = obj as? String
        
        if let string = string {
            return visualTransformation.filter(text: string).text
        } else {
            return nil
        }
    }
    
    override func getObjectValue(
        _ obj: AutoreleasingUnsafeMutablePointer<AnyObject?>?,
        for string: String,
        errorDescription error: AutoreleasingUnsafeMutablePointer<NSString?>?
    ) -> Bool {
        let result = visualTransformation.restore(text: string)
        obj?.pointee = result as AnyObject
        
        return true
    }
}
