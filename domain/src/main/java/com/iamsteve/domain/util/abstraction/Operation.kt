package com.iamsteve.domain.util.abstraction

interface Operation<IN, OUT> {
    fun execute(input: IN): OUT
}

fun <OUT> Operation<Unit, OUT>.execute(): OUT = execute(Unit)