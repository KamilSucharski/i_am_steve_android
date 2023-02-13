package com.iamsteve.data.util.di

import com.iamsteve.data.repository.ComicRepositoryImpl
import com.iamsteve.domain.repository.ComicRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ComicRepository> { ComicRepositoryImpl() }
}