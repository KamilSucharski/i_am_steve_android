package com.iamsteve.domain.view.base

interface BaseView<out T : Presenter<*>> {

    val presenter: T
}