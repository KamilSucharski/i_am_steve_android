package com.iamsteve.domain.view.base

interface View<out T : Presenter<*>> {
    val presenter: T
}