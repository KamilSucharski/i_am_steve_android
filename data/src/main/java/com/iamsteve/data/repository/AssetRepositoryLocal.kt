package com.iamsteve.data.repository

import com.iamsteve.data.util.abstraction.AssetReader
import com.iamsteve.domain.repository.AssetRepository
import java.io.InputStream

class AssetRepositoryLocal(private val assetReader: AssetReader) : AssetRepository.Local {

    override fun read(assetName: String): InputStream {
        return assetReader.read(assetName)
    }
}