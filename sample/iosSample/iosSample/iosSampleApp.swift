import SwiftUI
import sharedSample

@main
struct iosSampleApp: App {
    
    @StateObject
    private var rootHolder = RootHolder()
    
    var body: some Scene {
        WindowGroup {
            FormView(formComponent: rootHolder.formComponent)
                .onAppear { LifecycleRegistryExtKt.resume(self.rootHolder.lifecycle) }
                .onDisappear { LifecycleRegistryExtKt.stop(self.rootHolder.lifecycle) }
        }
    }
}

private class RootHolder : ObservableObject {
    let lifecycle: LifecycleRegistry
    let formComponent: FormComponent
    
    init() {
        lifecycle = LifecycleRegistryKt.LifecycleRegistry()
        formComponent = Core.shared.createFormComponent(
            componentContext: DefaultComponentContext(lifecycle: lifecycle)
        )
        lifecycle.onCreate()
    }
    
    deinit {
        lifecycle.onDestroy()
    }
}
