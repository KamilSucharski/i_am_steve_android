package com.iamsteve.data.data_source

import java.io.File
import java.io.Serializable
import java.lang.reflect.Type

interface LocalStorageDataSource {
    fun containsEntry(key: String): Boolean

    fun containsFile(key: String): Boolean

    fun removeEntry(key: String)

    fun removeFile(key: String)

    fun putBoolean(key: String, boolean: Boolean): Boolean

    fun putInt(key: String, int: Int): Int

    fun putLong(key: String, long: Long): Long

    fun putString(key: String, string: String): String

    fun putSerializable(key: String, serializable: Serializable): String

    fun putFile(key: String, byteArray: ByteArray): File

    fun getBoolean(key: String): Boolean?

    fun getInt(key: String): Int?

    fun getLong(key: String): Long?

    fun getString(key: String): String?

    fun <T> getSerializable(key: String, type: Type): T?

    fun getFile(key: String): File?
}