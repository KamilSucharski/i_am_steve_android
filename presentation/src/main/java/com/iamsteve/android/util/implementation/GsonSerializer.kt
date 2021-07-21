package com.iamsteve.android.util.implementation

import com.google.gson.Gson
import com.iamsteve.data.util.abstraction.Serializer
import java.lang.reflect.Type

class GsonSerializer(private val gson: Gson) : Serializer {

    override fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    override fun <T> deserialize(value: String, type: Type): T? {
        return try {
            gson.fromJson<T>(value, type)
        } catch (e: Exception) {
            null
        }
    }
}