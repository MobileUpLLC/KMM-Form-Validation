//
//  ScrolledFocusField.swift
//  iosSample
//
//  Created by Чаусов Николай on 09.02.2023.
//

import SwiftUI

private struct ScrolledFocusField: ViewModifier {
    
    @FocusState var focus: Int?
    
    let id: Int
    let nextId: Int
    let proxy: ScrollViewProxy
    let anchor: UnitPoint
    let nextAnchor: UnitPoint
    
    func body(content: Content) -> some View {
        content
            .focused($focus, equals: id)
            .onTapGesture {
                scrollToRowWithAnimation(proxy: proxy, id, anchor: anchor)
            }
            .onSubmit {
                scrollToRowWithAnimation(proxy: proxy, nextId, anchor: nextAnchor)
                focus = nextId
            }
    }
}

private extension ViewModifier {
    
    func scrollToRowWithAnimation(proxy: ScrollViewProxy, _ row: Int, anchor: UnitPoint = .center) {
        withAnimation {
            proxy.scrollTo(row, anchor: anchor)
        }
    }
}

extension View {
    
    func scrolledFocus(
        focus: FocusState<Int?>,
        id: Int,
        nextId: Int,
        proxy: ScrollViewProxy,
        anchor: UnitPoint = .center,
        nextAnchor: UnitPoint = .center
    ) -> some View {
        modifier(ScrolledFocusField(
            focus: focus,
            id: id,
            nextId: nextId,
            proxy: proxy,
            anchor: anchor,
            nextAnchor: nextAnchor
        ))
    }
}
