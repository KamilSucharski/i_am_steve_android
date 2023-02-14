package com.iamsteve.domain.util.abstraction

import org.koin.core.component.KoinComponent

interface Operation<OUT> : KoinComponent {
    fun execute(): OUT
}