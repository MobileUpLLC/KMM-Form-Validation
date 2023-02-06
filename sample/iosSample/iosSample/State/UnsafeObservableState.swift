import Foundation
import sharedSample

public class UnsafeObservableState<T: AnyObject>: ObservableObject {

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil

    init(_ state: Kotlinx_coroutines_coreStateFlow) {
        self.value = state.value as? T
        
        cancelable = FlowWrapper<T>(flow: state).collect(consumer: { value in
            self.value = value
        })
    }
   
    deinit {
        self.cancelable?.cancel()
    }
}

public class UnsafeMutableObservableState<T: AnyObject>: ObservableObject {

    private let wrapper: MutableStateFlowWrapper<T>

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil
    
    init(_ state: Kotlinx_coroutines_coreMutableStateFlow) {
        self.wrapper = MutableStateFlowWrapper<T>(stateFlow: state)
        self.value = state.value as? T

         cancelable = wrapper.collect(consumer: { value in
             self.value = value
         })
    }
    
    func setValue(value: T){
        wrapper.update(value: value)
    }

    deinit {
        self.cancelable?.cancel()
    }
}
