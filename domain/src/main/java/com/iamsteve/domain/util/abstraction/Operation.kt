package com.iamsteve.domain.util.abstraction

interface Operation<IN, OUT> {
    fun execute(input: IN): OUT
}