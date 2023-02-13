package com.iamsteve.android.util.di

import android.content.Context.MODE_PRIVATE
import com.iamsteve.android.util.implementation.AndroidAssetDataSource
import com.iamsteve.android.util.implementation.AndroidLocalStorageDataSource
import com.iamsteve.data.data_source.AssetDataSource
import com.iamsteve.data.data_source.LocalStorageDataSource
import com.iamsteve.domain.util.Consts
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val presentationDataSourceModule = module {
    single<LocalStorageDataSource> {
        AndroidLocalStorageDataSource(
            sharedPreferences = androidContext().getSharedPreferences(
                Consts.SHARED_PREFERENCES_NAME,
                MODE_PRIVATE
            ),
            filesDirectory = androidContext().filesDir,
            serializer = get()
        )
    }
    single<AssetDataSource> { AndroidAssetDataSource(assetManager = androidContext().assets) }
}