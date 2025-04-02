import SwiftUI
import Combine
import sharedSample

struct PickerView<T : NSObject, Content: View> : View {
    
    private let hint: String
    private let pickerControl: PickerControl<T>
    private let content: (@escaping () -> Void) -> Content
    
    @ObservedObject private var selectedValue: UnsafeObservableState<StringDesc>
    @ObservedObject private var error: UnsafeObservableState<StringDesc>
    @ObservedObject private var enabled: UnsafeObservableState<KotlinBoolean>
    
    @State private var isExpanded: Bool = false
    
    init(
        pickerControl: PickerControl<T>,
        hint: String,
        @ViewBuilder content: @escaping (@escaping () -> Void) -> Content
    ) {
        self.hint = hint
        self.pickerControl = pickerControl
        self.content = content
        
        self.selectedValue = UnsafeObservableState(pickerControl.displayValue)
        self.error = UnsafeObservableState(pickerControl.error)
        self.enabled = UnsafeObservableState(pickerControl.enabled)
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            Button(action: { isExpanded.toggle() }) {
                HStack {
                    Text(selectedValue.value?.localized() ?? hint)
                        .foregroundColor(selectedValue.value == nil ? .gray.opacity(0.5) : .primary)
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Image(systemName: "chevron.down")
                        .rotationEffect(.degrees(isExpanded ? -180 : 0))
                        .animation(.easeInOut(duration: 0.2), value: isExpanded)
                }
                .padding(6)
                .background(
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(error.value != nil ? .red : .gray.opacity(0.2), lineWidth: 1)
                )
                .disabled(!(enabled.value?.boolValue ?? true))
            }
            
            if isExpanded {
                content { isExpanded = false }
            }
            
            if let errorMessage = error.value?.localized() {
                Text(errorMessage)
                    .font(.caption)
                    .foregroundColor(.red)
            }
        }
    }
}
