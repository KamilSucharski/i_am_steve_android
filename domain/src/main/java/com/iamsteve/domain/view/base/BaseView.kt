package com.iamsteve.domain.view.base

interface BaseView<out T : BasePresenter<*>> {

    val presenter: T
}