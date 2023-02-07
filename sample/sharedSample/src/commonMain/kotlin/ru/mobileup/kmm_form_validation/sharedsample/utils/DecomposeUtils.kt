package ru.mobileup.kmm_form_validation.sharedsample.utils

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Returns a coroutine scope tied to Decompose lifecycle. A scope is canceled when a component is destroyed.
 */
val ComponentContext.componentScope: CoroutineScope
    get() {
        val scope = (instanceKeeper.get(ComponentScopeKey) as? CoroutineScopeWrapper)?.scope
        if (scope != null) return scope

        val newScope = lifecycle.createComponentScope()
        instanceKeeper.put(ComponentScopeKey, CoroutineScopeWrapper(newScope))
        return newScope
    }

private object ComponentScopeKey

private class CoroutineScopeWrapper(val scope: CoroutineScope) : InstanceKeeper.Instance {
    override fun onDestroy() {
        // nothing
    }
}

private fun Lifecycle.createComponentScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    if (this.state != Lifecycle.State.DESTROYED) {
        this.doOnDestroy {
            scope.cancel()
        }
    } else {
        scope.cancel()
    }

    return scope
}