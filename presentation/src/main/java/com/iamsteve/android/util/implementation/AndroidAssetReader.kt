package com.iamsteve.android.util.implementation

import android.content.res.AssetManager
import com.iamsteve.data.util.abstraction.AssetReader
import java.io.InputStream

class AndroidAssetReader(private val assetManager: AssetManager) : AssetReader {

    override fun read(assetName: String): InputStream {
        return assetManager.open(assetName);
    }
}