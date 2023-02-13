package com.iamsteve.domain.util.extension

import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.error.ErrorHandler
import io.reactivex.rxjava3.core.Observable

fun <T : Any> Observable<T>.handleError(errorHandler: ErrorHandler, default: T? = null): Observable<T> {
    val resumeFunction: (Throwable) -> Observable<T> = { error ->
        errorHandler.handleError(error)
        default?.let { Observable.just(it) } ?: Observable.empty()
    }

    return onErrorResumeNext(resumeFunction)
}

fun <T : Any> Observable<T>.schedule(schedulers: RxSchedulers): Observable<T> {
    return subscribeOn(schedulers.subscribeThread).observeOn(schedulers.observeThread)
}