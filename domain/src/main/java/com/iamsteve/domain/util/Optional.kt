package com.iamsteve.domain.util

class Optional<T> {

    private var value: T? = null

    private constructor() {
        this.value = null
    }

    private constructor(value: T?) {
        this.value = value
    }

    val isPresent: Boolean
        get() = value != null

    fun get(): T = value!!

    companion object {

        fun <T> empty(): Optional<T> = Optional()

        fun <T> of(value: T?): Optional<T> = Optional(value)

    }

}