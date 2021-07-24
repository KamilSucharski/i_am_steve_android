package com.iamsteve.data.repository

import com.google.gson.reflect.TypeToken
import com.iamsteve.data.util.abstraction.AssetReader
import com.iamsteve.data.util.abstraction.LocalStorage
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.exception.NoComicPanelException
import com.iamsteve.domain.exception.NoComicsException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import io.reactivex.Single
import java.io.BufferedReader
import java.io.File

class ComicRepositoryLocal(
    private val assetReader: AssetReader,
    private val localStorage: LocalStorage,
    private val serializer: Serializer
) : ComicRepository.Local {

    override fun getComicsFromAssets(): Single<List<Comic>> {
        return assetReader
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
    }

    override fun getComicsFromLocalStorage(): Single<List<Comic>> {
        return localStorage
            .getSerializable<List<Comic>>(
                key = Consts.KEY_COMIC_LIST,
                type = object : TypeToken<List<Comic>>() {}.type
            )
            ?.let { Single.just(it) }
            ?: Single.error(NoComicsException())
    }

    override fun saveComicsToLocalStorage(comics: List<Comic>) {
        localStorage.putSerializable(
            key = Consts.KEY_COMIC_LIST,
            serializable = ArrayList(comics)
        )
    }

    override fun getComicPanelFromAssets(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return Single.fromCallable {
            val assetName = String.format(
                Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                comicNumber,
                panelNumber
            )
            assetReader.read(assetName).readBytes()
        }.onErrorResumeNext { Single.error(NoComicPanelException()) }
    }

    override fun getComicPanelFromLocalStorage(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        val fileName = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber)
        return localStorage.getFile(key = fileName)
            ?.let { Single.just(it.readBytes()) }
            ?: Single.error(NoComicPanelException())
    }

    override fun saveComicPanelToLocalStorage(
        comicNumber: Int,
        panelNumber: Int,
        byteArray: ByteArray
    ): ByteArray {
        localStorage.putFile(
            key = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber),
            byteArray = byteArray
        )
        return byteArray
    }

    override fun removeComicPanelFromLocalStorage(comicNumber: Int, panelNumber: Int) {
        localStorage.removeFile(
            key = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber)
        )
    }
}