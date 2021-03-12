package com.iamsteve.android.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.iamsteve.android.util.implementation.AndroidLocalStorage
import com.iamsteve.android.util.implementation.AndroidLogger
import com.iamsteve.android.util.implementation.AndroidRxSchedulers
import com.iamsteve.android.util.persistence.Persistence
import com.iamsteve.data.util.LocalStorage
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.Logger
import com.iamsteve.domain.util.RxSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single<RxSchedulers> { AndroidRxSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }
    single<SharedPreferences> {
        EncryptedSharedPreferences.create(
            androidContext(),
            Consts.SHARED_PREFERENCES_NAME,
            MasterKey.Builder(androidContext()).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    factory<LocalStorage> {
        AndroidLocalStorage(
            sharedPreferences = get(),
            filesDirectory = androidContext().filesDir
        )
    }
    factory<Logger> { AndroidLogger() }
    factory { Persistence(get()) }
}