package com.iamsteve.android.di

import android.content.Context.MODE_PRIVATE
import com.iamsteve.android.BuildConfig
import com.iamsteve.android.util.implementation.AndroidLocalStorage
import com.iamsteve.android.util.implementation.AndroidLogger
import com.iamsteve.android.util.implementation.AndroidPreloader
import com.iamsteve.android.util.implementation.AndroidRxSchedulers
import com.iamsteve.android.util.persistence.Persistence
import com.iamsteve.data.util.LocalStorage
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.Logger
import com.iamsteve.domain.util.Preloader
import com.iamsteve.domain.util.RxSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single<RxSchedulers> { AndroidRxSchedulers(AndroidSchedulers.mainThread(), Schedulers.io()) }
    factory<LocalStorage> {
        AndroidLocalStorage(
            sharedPreferences = androidContext().getSharedPreferences(
                Consts.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
            ),
            filesDirectory = androidContext().filesDir,
            gson = get()
        )
    }
    factory<Logger> { AndroidLogger() }
    factory { Persistence(get()) }
    factory<Preloader> {
        AndroidPreloader(
            currentVersion = BuildConfig.VERSION_CODE,
            assetManager = androidContext().assets,
            localStorage = get(),
            gson = get()
        )
    }
}