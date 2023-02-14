package com.iamsteve.domain.util

interface Presenter<V> {

    fun subscribeView(view: V)

    fun unsubscribe()
}