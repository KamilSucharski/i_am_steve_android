package com.iamsteve.domain.util.extension

@Suppress("UNCHECKED_CAST")
fun <T> Any.cast(): T? {
    return this as? T
}