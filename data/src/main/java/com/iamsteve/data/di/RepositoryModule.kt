package com.iamsteve.data.di

import com.iamsteve.data.repository.ComicRepositoryLocal
import com.iamsteve.data.repository.ComicRepositoryRemote
import com.iamsteve.domain.repository.ComicRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<ComicRepository.Local> { ComicRepositoryLocal(get(), get(), get()) }
    factory<ComicRepository.Remote> { ComicRepositoryRemote(get(), get()) }
}