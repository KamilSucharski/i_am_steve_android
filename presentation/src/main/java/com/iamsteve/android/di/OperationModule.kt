package com.iamsteve.android.di

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import org.koin.dsl.module

val operationModule = module {
    factory {
        GetComicsOperation(
            assetRepositoryLocal = get(),
            comicRepositoryLocal = get(),
            comicRepositoryRemote = get(),
            schedulers = get(),
            logger = get()
        )
    }

    factory {
        GetComicPanelsOperation(
            assetRepositoryLocal = get(),
            comicRepositoryLocal = get(),
            comicRepositoryRemote = get(),
            schedulers = get(),
            logger = get()
        )
    }
}