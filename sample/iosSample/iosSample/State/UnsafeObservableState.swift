//
//  ObservableState.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import Foundation
import sharedSample

public class UnsafeObservableState<T: AnyObject>: ObservableObject {

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil

    convenience init(_ state: Kotlinx_coroutines_coreStateFlow) {
        self.init(value: state.value as? T, flow: state)
    }
    
    init(value: T? = nil, flow: Kotlinx_coroutines_coreFlow) {
        self.value = value
        
        cancelable = FlowWrapper<T>(flow: flow).collect(consumer: { value in
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
        self.value = value

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
