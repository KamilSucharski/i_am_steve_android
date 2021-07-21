package com.iamsteve.data.repository

import com.google.gson.reflect.TypeToken
import com.iamsteve.data.util.abstraction.AssetReader
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.exception.NoComicsException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.AssetRepository
import com.iamsteve.domain.util.Consts
import io.reactivex.Single
import java.io.BufferedReader

class AssetRepositoryLocal(
    private val assetReader: AssetReader,
    private val serializer: Serializer
) : AssetRepository.Local {

    override fun getComics(): Single<List<Comic>> = assetReader
        .read(Consts.COMIC_METADATA_FILE_NAME)
        .bufferedReader()
        .use(BufferedReader::readText)
        .let {
            serializer.deserialize<List<Comic>>(
                value = it,
                type = object : TypeToken<List<Comic>>() {}.type
            )
        }
        ?.let { Single.just(it) }
        ?: Single.error(NoComicsException())

    override fun getComicPanel(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return Single.fromCallable {
            val assetName = String.format(
                Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                comicNumber,
                panelNumber
            )
            assetReader.read(assetName).readBytes()
        }
    }
}