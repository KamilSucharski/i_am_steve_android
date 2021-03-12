package com.iamsteve.domain.view

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter<V> : BasePresenter<V> {

    protected val disposables = CompositeDisposable()

    override fun unsubscribe() {
        disposables.clear()
    }
}