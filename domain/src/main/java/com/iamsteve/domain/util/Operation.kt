package com.iamsteve.domain.util

interface Operation<T> {
    fun execute(): T
}