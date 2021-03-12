package com.iamsteve.domain.view

interface BasePresenter<V> {

    fun subscribe(view: V)

    fun unsubscribe()
}