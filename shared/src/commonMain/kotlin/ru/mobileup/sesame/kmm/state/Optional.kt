package ru.mobileup.sesame.kmm.state


class Optional<T : Any>(private val value: T? = null) {
    fun getForce(): T = value!!
    fun get(): T? = value
}

fun <T : Any> T?.asOptional(): Optional<T> = Optional(this)
