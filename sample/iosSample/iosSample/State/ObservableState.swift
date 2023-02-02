//
//  ObservableState.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import Foundation
import sharedSample

public class ObservableState<T: AnyObject>: ObservableObject {

    private let observableState: FlowWrapper<T>

    @Published
    var value: T

    private var cancelable: Cancelable? = nil

    convenience init(_ state: CStateFlow<T>) {
        self.init(value: state.value, flow: state)
    }
    
    init(value: T, flow: Kotlinx_coroutines_coreFlow) {
        self.observableState = FlowWrapper<T>(flow: flow)
        self.value = value
        
        cancelable = observableState.bind(consumer: { value in
            self.value = value
        })
    }

    deinit {
        self.cancelable?.cancel()
    }
}

public class ObservableFlow<T: AnyObject>: ObservableObject {

    private let observableFlow: FlowWrapper<T>

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil

    init(flow: Kotlinx_coroutines_coreFlow) {
        self.observableFlow = FlowWrapper<T>(flow: flow)
        
        cancelable = observableFlow.bind(consumer: { value in
            self.value = value
        })
    }

    deinit {
        self.cancelable?.cancel()
    }
}

public class MutableObservableState<T: AnyObject>: ObservableObject {

    private let observableState: MutableStateWrapper<T>

    @Published
    var value: T

    private var cancelable: Cancelable? = nil

    init(_ state: CMutableStateFlow<T>) {
        self.observableState = MutableStateWrapper<T>(stateFlow: state)
        self.value = state.value

         cancelable = observableState.bind(consumer: { value in
             self.value = value
         })
    }
    
    func setValue(value: T){
        observableState.update(value: value)
    }

    deinit {
        self.cancelable?.cancel()
    }
}
