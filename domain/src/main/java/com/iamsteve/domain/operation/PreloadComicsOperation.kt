package com.iamsteve.domain.operation

import com.iamsteve.domain.util.Logger
import com.iamsteve.domain.util.Operation
import com.iamsteve.domain.util.Preloader
import org.koin.core.KoinComponent
import org.koin.core.inject

class PreloadComicsOperation : Operation<Unit>, KoinComponent {

    private val prelaoder by inject<Preloader>()
    private val logger by inject<Logger>()

    override fun execute() {
        logger.debug("Preloading started")
        prelaoder.preload()
        logger.debug("Preloading completed")
    }
}