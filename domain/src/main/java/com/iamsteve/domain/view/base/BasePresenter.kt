package com.iamsteve.domain.view.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.addTo

abstract class BasePresenter<V> : Presenter<V> {

    private val disposables = CompositeDisposable()

    override fun subscribeView(view: V) {}

    override fun unsubscribe() {
        disposables.clear()
    }

    protected fun Disposable.autoDispose() {
        addTo(disposables)
    }
}