package com.iamsteve.data.di

import com.iamsteve.data.repository.AssetRepositoryLocal
import com.iamsteve.data.repository.ComicRepositoryLocal
import com.iamsteve.data.repository.ComicRepositoryRemote
import com.iamsteve.domain.repository.AssetRepository
import com.iamsteve.domain.repository.ComicRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory<AssetRepository.Local> { AssetRepositoryLocal(get()) }
    factory<ComicRepository.Local> { ComicRepositoryLocal(get()) }
    factory<ComicRepository.Remote> { ComicRepositoryRemote(get(), get()) }
}