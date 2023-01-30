//
//  ContentView.swift
//  iosSample
//
//  Created by Andrey on 30.01.2023.
//

import SwiftUI
import shared

struct ContentView: View {
    
    var inputControl: InputControl = InputControl(coroutineScope: IosCoroutineScopeKt.IosCoroutineScope)
    
    @State private var username: String = ""

    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundColor(.accentColor)
            Text("Hello, world!")
            TextField("Username", text: $username)
        }
        .padding()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

class inControl{
    
    let inputControl: InputControl
    
    init(inputControl: InputControl){
        self.inputControl = inputControl
    }
    
    @State
    var text: String = inputControl.text
}
