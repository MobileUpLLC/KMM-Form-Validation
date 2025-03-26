import SwiftUI
import sharedSample

struct ToggleView: View {
    
    let checkControl: CheckControl
    let label: String
    
    @ObservedObject var checked: UnsafeObservableState<KotlinBoolean>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    
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
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .font(.caption)
                    .foregroundColor(.red)
            }
        }
        .animation(Animation.easeInOut(duration: 0.3), value: UUID())
    }
}
