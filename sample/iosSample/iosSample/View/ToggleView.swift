import SwiftUI
import sharedSample

struct ToggleView: View {
    @ObservedObject var checked: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    
    let checkControl: CheckControl
    let label: String
    
    init(checkControl: CheckControl, label: String) {
        self.label = label
        self.checkControl = checkControl
        checked = UnsafeObservableState(checkControl.checked)
        error = UnsafeObservableState(checkControl.error)
    }
    
    var body: some View {
        VStack {
            Toggle(
                isOn: Binding(
                    get: { checked.value?.boolValue ?? false },
                    set: checkControl.onCheckedChanged
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
