package com.iamsteve.domain.repository

import java.io.InputStream

interface AssetRepository {

    interface Local {
        fun read(assetName: String): InputStream
    }
}