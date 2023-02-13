package com.iamsteve.domain.view.base

interface Presenter<V> {

    fun subscribeView(view: V)

    fun unsubscribe()
}