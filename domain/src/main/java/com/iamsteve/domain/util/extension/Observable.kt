package com.iamsteve.domain.util.extension

import com.iamsteve.domain.util.RxSchedulers
import com.iamsteve.domain.util.error.ErrorHandler
import io.reactivex.Observable

fun <T> Observable<T>.catchError(handler: (Throwable) -> Observable<T>): Observable<T> {
    return onErrorResumeNext(handler)
}

fun <T> Observable<T>.handleError(errorHandler: ErrorHandler, default: T? = null): Observable<T> {
    return catchError { error ->
        errorHandler.handleError(error)
        default?.let { Observable.just(it) } ?: Observable.empty()
    }
}

fun <T> Observable<T>.schedule(schedulers: RxSchedulers): Observable<T> {
    return subscribeOn(schedulers.subscribeThread).observeOn(schedulers.observeThread)
}