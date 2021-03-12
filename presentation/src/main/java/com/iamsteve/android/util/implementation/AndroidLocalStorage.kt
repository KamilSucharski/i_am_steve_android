package com.iamsteve.android.util.implementation

import android.content.SharedPreferences
import com.google.gson.Gson
import com.iamsteve.data.util.LocalStorage
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.Optional
import java.io.File
import java.io.Serializable
import java.lang.reflect.Type

class AndroidLocalStorage(
    private val sharedPreferences: SharedPreferences,
    private val filesDirectory: File
) : LocalStorage {

    private val gson = Gson()

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
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
        val serializedValue = gson.toJson(serializable)
        sharedPreferences.edit().putString(key, serializedValue).apply()
        return serializedValue
    }

    override fun putFile(key: String, byteArray: ByteArray): File {
        val file = File(filesDirectory, key)
        file.writeBytes(byteArray)
        return file
    }

    override fun getBoolean(key: String): Optional<Boolean> {
        return if (sharedPreferences.contains(key)) {
            Optional.of(sharedPreferences.getBoolean(key, false))
        } else {
            Optional.empty()
        }
    }

    override fun getInt(key: String): Optional<Int> {
        return if (sharedPreferences.contains(key)) {
            Optional.of(sharedPreferences.getInt(key, 0))
        } else {
            Optional.empty()
        }
    }

    override fun getLong(key: String): Optional<Long> {
        return if (sharedPreferences.contains(key)) {
            Optional.of(sharedPreferences.getLong(key, 0))
        } else {
            Optional.empty()
        }
    }

    override fun getString(key: String): Optional<String> {
        return if (sharedPreferences.contains(key)) {
            Optional.of(sharedPreferences.getString(key, Consts.EMPTY))
        } else {
            Optional.empty()
        }
    }

    override fun <T> getSerializable(key: String, type: Type): Optional<T> {
        return if (sharedPreferences.contains(key)) {
            val serializedValue = sharedPreferences.getString(key, Consts.EMPTY)
            Optional.of(gson.fromJson(serializedValue, type))
        } else {
            Optional.empty()
        }
    }

    override fun getFile(key: String): Optional<File> {
        val file = File(filesDirectory, key)
        return if (file.exists()) {
            Optional.of(file)
        } else {
            Optional.empty()
        }
    }
}