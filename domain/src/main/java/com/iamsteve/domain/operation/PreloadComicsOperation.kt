package com.iamsteve.domain.operation

import com.iamsteve.domain.util.Logger
import com.iamsteve.domain.util.Operation
import com.iamsteve.domain.util.Preloader
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

class PreloadComicsOperation : Operation<Observable<Boolean>>, KoinComponent {

    private val preloader by inject<Preloader>()
    private val logger by inject<Logger>()

    override fun execute(): Observable<Boolean> {
        logger.debug("Preloading started")
        return Observable.fromCallable {
            preloader.preload()
            logger.debug("Preloading completed")
            true
        }.onErrorReturn {
            logger.error("Preloading failed", it)
            false
        }
    }
}