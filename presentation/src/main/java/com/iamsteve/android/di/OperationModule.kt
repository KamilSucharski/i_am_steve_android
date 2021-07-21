package com.iamsteve.android.di

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import org.koin.dsl.module

val operationModule = module {
    factory {
        GetComicsOperation(
            comicRepositoryLocal = get(),
            comicRepositoryRemote = get(),
            schedulers = get(),
            logger = get()
        )
    }

    factory {
        GetComicPanelsOperation(
            comicRepositoryLocal = get(),
            comicRepositoryRemote = get(),
            schedulers = get(),
            logger = get()
        )
    }
}