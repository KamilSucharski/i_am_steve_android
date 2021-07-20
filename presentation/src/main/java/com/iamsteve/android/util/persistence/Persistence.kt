package com.iamsteve.android.util.persistence

import android.os.Bundle
import com.iamsteve.domain.util.abstraction.Logger
import java.io.Serializable
import java.lang.reflect.Field

class Persistence(
    private val logger: Logger
) {

    fun saveFields(instance: Any, bundle: Bundle) {
        doOnFields(instance) { field -> saveField(field, instance, bundle) }
    }

    fun loadFields(instance: Any, bundle: Bundle) {
        doOnFields(instance) { field -> loadField(field, instance, bundle) }
    }

    private fun doOnFields(instance: Any, operation: (Field) -> Unit) {
        instance
            .javaClass
            .declaredFields
            .toList()
            .filter { field -> field.isAnnotationPresent(Persist::class.java) }
            .forEach(operation)
    }

    private fun saveField(field: Field, instance: Any, bundle: Bundle) {
        try {
            val isAccessible = field.isAccessible
            field.isAccessible = true
            val key = field.name
            val value = field[instance] as Serializable
            field.isAccessible = isAccessible
            bundle.putSerializable(key, value)
        } catch (e: Exception) {
            logger.error("Error persisting field", e)
        }
    }

    private fun loadField(field: Field, instance: Any, bundle: Bundle) {
        try {
            val isAccessible = field.isAccessible
            field.isAccessible = true
            val key = field.name
            val value = bundle.getSerializable(key)
            field[instance] = value
            field.isAccessible = isAccessible
        } catch (e: Exception) {
            logger.error("Error restoring field", e)
        }
    }
}