package com.iamsteve.domain.view

interface BaseView<out T : BasePresenter<*>> {

    val presenter: T
}