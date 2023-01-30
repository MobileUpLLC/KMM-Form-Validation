package ru.mobileup.sesame.kmm.form

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

val IosCoroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)