package com.iamsteve.android.util.implementation

import android.content.res.AssetManager
import com.iamsteve.data.data_source.AssetDataSource
import java.io.InputStream

class AndroidAssetDataSource(private val assetManager: AssetManager) : AssetDataSource {

    override fun read(assetName: String): InputStream {
        return assetManager.open(assetName);
    }
}