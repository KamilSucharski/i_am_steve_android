package com.iamsteve.domain.util.di

import org.koin.core.module.Module

interface KoinModule<T> {
    fun provide(parameters: T): List<Module>
}
