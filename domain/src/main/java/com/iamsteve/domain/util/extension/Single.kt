package com.iamsteve.domain.util.extension

import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.error.ErrorHandler
import io.reactivex.Single

fun <T> Single<T>.handleError(errorHandler: ErrorHandler, default: T? = null): Single<T> {
    return onErrorResumeNext { error ->
        errorHandler.handleError(error)
        default?.let { Single.just(it) } ?: Single.never()
    }
}

fun <T> Single<T>.schedule(schedulers: RxSchedulers): Single<T> {
    return subscribeOn(schedulers.subscribeThread).observeOn(schedulers.observeThread)
}