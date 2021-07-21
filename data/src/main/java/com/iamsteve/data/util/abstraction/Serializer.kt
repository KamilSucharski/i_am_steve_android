package com.iamsteve.data.util.abstraction

import java.lang.reflect.Type

interface Serializer {

    fun <T> serialize(value: T): String

    fun <T> deserialize(value: String, type: Type): T?
}