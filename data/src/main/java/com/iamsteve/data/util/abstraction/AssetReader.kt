package com.iamsteve.data.util.abstraction

import java.io.InputStream

interface AssetReader {
    fun read(assetName: String): InputStream
}