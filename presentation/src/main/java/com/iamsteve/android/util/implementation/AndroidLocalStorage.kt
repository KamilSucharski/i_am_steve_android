package com.iamsteve.android.util.implementation

import android.content.SharedPreferences
import com.iamsteve.data.util.abstraction.LocalStorage
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.util.Consts
import java.io.File
import java.io.Serializable
import java.lang.reflect.Type

class AndroidLocalStorage(
    private val sharedPreferences: SharedPreferences,
    private val filesDirectory: File,
    private val serializer: Serializer
) : LocalStorage {

    override fun containsEntry(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun containsFile(key: String): Boolean {
        return File(filesDirectory, key).exists()
    }

    override fun removeEntry(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun removeFile(key: String) {
        val file = File(filesDirectory, key)
        if (file.exists()) {
            file.delete()
        }
    }

    override fun putBoolean(key: String, boolean: Boolean): Boolean {
        sharedPreferences.edit().putBoolean(key, boolean).apply()
        return boolean
    }

    override fun putInt(key: String, int: Int): Int {
        sharedPreferences.edit().putInt(key, int).apply()
        return int
    }

    override fun putLong(key: String, long: Long): Long {
        sharedPreferences.edit().putLong(key, long).apply()
        return long
    }

    override fun putString(key: String, string: String): String {
        sharedPreferences.edit().putString(key, string).apply()
        return string
    }

    override fun putSerializable(key: String, serializable: Serializable): String {
        val serializedValue = serializer.serialize(serializable)
        sharedPreferences.edit().putString(key, serializedValue).apply()
        return serializedValue
    }

    override fun putFile(key: String, byteArray: ByteArray): File {
        val file = File(filesDirectory, key)
        file.writeBytes(byteArray)
        return file
    }

    override fun getBoolean(key: String): Boolean? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getBoolean(key, false)
        } else {
            null
        }
    }

    override fun getInt(key: String): Int? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getInt(key, 0)
        } else {
            null
        }
    }

    override fun getLong(key: String): Long? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getLong(key, 0)
        } else {
            null
        }
    }

    override fun getString(key: String): String? {
        return if (sharedPreferences.contains(key)) {
            sharedPreferences.getString(key, Consts.EMPTY)
        } else {
            null
        }
    }

    override fun <T> getSerializable(key: String, type: Type): T? {
        return if (sharedPreferences.contains(key)) {
            val serializedValue = sharedPreferences
                .getString(key, Consts.EMPTY)
                ?: Consts.EMPTY
            serializer.deserialize<T>(
                value = serializedValue,
                type = type
            )
        } else {
            null
        }
    }

    override fun getFile(key: String): File? {
        val file = File(filesDirectory, key)
        return if (file.exists()) {
            file
        } else {
            null
        }
    }
}