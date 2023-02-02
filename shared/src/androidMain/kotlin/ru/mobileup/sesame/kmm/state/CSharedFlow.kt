package ru.mobileup.sesame.kmm.state

import kotlinx.coroutines.flow.SharedFlow

actual open class CSharedFlow<T : Any> actual constructor(sharedFlow: SharedFlow<T>) :
    SharedFlow<T> by sharedFlow