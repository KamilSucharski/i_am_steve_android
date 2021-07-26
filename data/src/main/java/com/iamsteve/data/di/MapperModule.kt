package com.iamsteve.data.di

import com.iamsteve.data.dto.ComicDto
import com.iamsteve.data.mapper.ComicMapper
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.abstraction.Mapper
import org.koin.dsl.module

val mapperModule = module {
    factory<Mapper<ComicDto, Comic>> { ComicMapper() }
}