package com.iamsteve.android.util.implementation

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iamsteve.domain.util.abstraction.Serializer

class GsonSerializer(private val gson: Gson) : Serializer {

    override fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    override fun <T> deserialize(value: String): T? {
        return try {
            gson.fromJson<T>(value, object : TypeToken<T>() {}.type)
        } catch (e: Exception) {
            null
        }
    }
}