package com.iamsteve.domain.view.base

interface BasePresenter<V> {

    fun subscribe(view: V)

    fun unsubscribe()
}