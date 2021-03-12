package com.iamsteve.domain.util.di

import org.koin.core.module.Module

interface Module<T> {

    fun provide(parameters: T): List<Module>
}