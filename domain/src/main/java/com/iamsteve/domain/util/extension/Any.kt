package com.iamsteve.domain.util.extension

fun <T> Any.cast(): T? {
    return this as? T
}