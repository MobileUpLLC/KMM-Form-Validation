//
//  ObservableState.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import Foundation
import sharedSample

public class ObservableFlow<T: AnyObject>: ObservableObject {

    private let observableState: FlowWrapper<T>

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil

    convenience init(_ state: Kotlinx_coroutines_coreStateFlow) {
        self.init(value: state.value as? T, flow: state)
    }
    
    init(value: T? = nil, flow: Kotlinx_coroutines_coreFlow) {
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

public class MutableObservableFlow<T: AnyObject>: ObservableObject {

    private let observableState: MutableFlowWrapper<T>

    @Published
    var value: T?

    private var cancelable: Cancelable? = nil

    convenience init(_ state: Kotlinx_coroutines_coreMutableStateFlow) {
        self.init(value: state.value as? T, state)
    }
    
    init(value: T? = nil, _ mutableFlow: Kotlinx_coroutines_coreMutableSharedFlow) {
        self.observableState = MutableFlowWrapper<T>(mutableFlow: mutableFlow)
        self.value = value

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
