package com.iamsteve.data.data_source

import java.io.InputStream

interface AssetDataSource {
    fun read(assetName: String): InputStream
}