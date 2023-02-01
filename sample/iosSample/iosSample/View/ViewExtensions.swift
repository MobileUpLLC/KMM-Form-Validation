//
//  ViewExtensions.swift
//  iosSample
//
//  Created by Andrey on 31.01.2023.
//

import Foundation
import SwiftUI
import sharedSample

extension View {
    func getBinding<T>(state: MutableObservableState<T>) -> Binding<T> {
        return Binding {
            state.value
        } set: { value in
            state.setValue(value: value)
        }
    }
    
    func getBinding<T, K>(state: MutableObservableState<T>,
                          transform: @escaping (T) -> K,
                          reverse: @escaping (K) -> T) -> Binding<K> {
        return Binding {
            transform(state.value)
        } set: { value in
            state.setValue(value: reverse(value))
        }
    }
    
    func getBindingForString(state: MutableObservableState<NSString>) -> Binding<String> {
        return Binding {
            String(state.value)
        } set: { value in
            state.setValue(value: NSString(string: value))
        }
    }
}
