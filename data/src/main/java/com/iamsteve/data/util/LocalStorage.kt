package com.iamsteve.data.util

import com.iamsteve.domain.util.Optional
import java.io.File
import java.io.Serializable
import java.lang.reflect.Type

interface LocalStorage {

    fun contains(key: String): Boolean

    fun remove(key: String)

    fun putBoolean(key: String, boolean: Boolean): Boolean

    fun putInt(key: String, int: Int): Int

    fun putLong(key: String, long: Long): Long

    fun putString(key: String, string: String): String

    fun putSerializable(key: String, serializable: Serializable): String

    fun putFile(key: String, byteArray: ByteArray): File

    fun getBoolean(key: String): Optional<Boolean>

    fun getInt(key: String): Optional<Int>

    fun getLong(key: String): Optional<Long>

    fun getString(key: String): Optional<String>

    fun <T> getSerializable(key: String, type: Type): Optional<T>

    fun getFile(key: String): Optional<File>
}