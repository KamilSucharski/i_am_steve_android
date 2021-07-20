package com.iamsteve.data.di

import com.iamsteve.data.mapper.ComicMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { ComicMapper() }
}