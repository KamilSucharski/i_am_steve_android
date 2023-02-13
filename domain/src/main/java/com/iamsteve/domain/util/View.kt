package com.iamsteve.domain.util

interface View<out T : Presenter<*>> {
    val presenter: T
}