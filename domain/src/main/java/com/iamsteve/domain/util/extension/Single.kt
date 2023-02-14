package com.iamsteve.domain.util.extension

import com.iamsteve.domain.util.abstraction.ReactiveSchedulers
import com.iamsteve.domain.util.error.ErrorHandler
import io.reactivex.rxjava3.core.Single

fun <T : Any> Single<T>.handleError(errorHandler: ErrorHandler, default: T? = null): Single<T> {
    return onErrorResumeNext { error ->
        errorHandler.handleError(error)
        default?.let { Single.just(it) } ?: Single.never()
    }
}

fun <T : Any> Single<T>.schedule(schedulers: ReactiveSchedulers): Single<T> {
    return subscribeOn(schedulers.subscribeThread).observeOn(schedulers.observeThread)
}