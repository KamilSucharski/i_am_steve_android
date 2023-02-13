package com.iamsteve.data.repository

import com.google.gson.reflect.TypeToken
import com.iamsteve.data.data_source.ApiDataSource
import com.iamsteve.data.data_source.AssetDataSource
import com.iamsteve.data.data_source.LocalStorageDataSource
import com.iamsteve.data.mapper.ComicMapper
import com.iamsteve.data.util.abstraction.Serializer
import com.iamsteve.domain.exception.NoComicPanelException
import com.iamsteve.domain.exception.NoComicsException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.map
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.BufferedReader

class ComicRepositoryImpl : ComicRepository, KoinComponent {

    private val apiDataSource by inject<ApiDataSource>()
    private val assetDataSource by inject<AssetDataSource>()
    private val localStorageDataSource by inject<LocalStorageDataSource>()
    private val logger by inject<Logger>()
    private val serializer by inject<Serializer>()

    override fun getComics(): Single<List<Comic>> = getComicsFromApiAndSaveToLocalStorage()
        .onErrorResumeNext {
            logger.error("Couldn't get comics.json from the API. Trying local storage.", it)
            getComicsFromLocalStorage()
        }
        .onErrorResumeNext {
            logger.error("Couldn't get comics.json from the local storage. Trying assets.", it)
            getComicsFromAssets()
        }

    override fun getComicPanels(
        comicNumber: Int
    ): Single<ComicPanels> = getComicPanelsFromAssetsAndRemoveFromLocalStorage(comicNumber)
        .onErrorResumeNext {
            logger.error("Couldn't get comic panels from the assets. Trying local storage.", it)
            getComicPanelsFromLocalStorage(comicNumber)
        }
        .onErrorResumeNext {
            logger.error("Couldn't get comic panels from the local storage. Trying API.", it)
            getComicPanelsFromApiAndSaveToLocalStorage(comicNumber)
        }

    private fun getComicsFromApiAndSaveToLocalStorage(): Single<List<Comic>> = apiDataSource
        .getComics()
        .map { it.map(ComicMapper()) }
        .map {
            localStorageDataSource.putSerializable(
                key = Consts.KEY_COMIC_LIST,
                serializable = ArrayList(it)
            )
            it
        }

    private fun getComicsFromLocalStorage(): Single<List<Comic>> = localStorageDataSource
        .getSerializable<List<Comic>>(
            key = Consts.KEY_COMIC_LIST,
            type = object : TypeToken<List<Comic>>() {}.type
        )
        ?.let { Single.just(it) }
        ?: Single.error(NoComicsException())

    private fun getComicsFromAssets(): Single<List<Comic>> = assetDataSource
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

    private fun getComicPanelsFromAssetsAndRemoveFromLocalStorage(
        comicNumber: Int
    ): Single<ComicPanels> = joinPanels(
        panel1Single = getComicPanelFromAssetsAndRemoveFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 1
        ),
        panel2Single = getComicPanelFromAssetsAndRemoveFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 2
        ),
        panel3Single = getComicPanelFromAssetsAndRemoveFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 3
        ),
        panel4Single = getComicPanelFromAssetsAndRemoveFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 4
        )
    )

    private fun getComicPanelFromAssetsAndRemoveFromLocalStorage(
        comicNumber: Int,
        panelNumber: Int
    ): Single<ByteArray> = Single
        .fromCallable {
            val assetName = String.format(
                Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                comicNumber,
                panelNumber
            )
            assetDataSource.read(assetName).readBytes()
        }
        .onErrorResumeNext { Single.error(NoComicPanelException()) }
        .map {
            localStorageDataSource.removeFile(
                key = String.format(
                    Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                    comicNumber,
                    panelNumber
                )
            )
            it
        }

    private fun getComicPanelsFromLocalStorage(
        comicNumber: Int
    ): Single<ComicPanels> = joinPanels(
        panel1Single = getComicPanelFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 1
        ),
        panel2Single = getComicPanelFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 2
        ),
        panel3Single = getComicPanelFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 3
        ),
        panel4Single = getComicPanelFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 4
        )
    )

    private fun getComicPanelFromLocalStorage(
        comicNumber: Int,
        panelNumber: Int
    ): Single<ByteArray> = localStorageDataSource
        .getFile(key = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber))
        ?.let { Single.just(it.readBytes()) }
        ?: Single.error(NoComicPanelException())

    private fun getComicPanelsFromApiAndSaveToLocalStorage(
        comicNumber: Int
    ): Single<ComicPanels> = joinPanels(
        panel1Single = getComicPanelFromApiAndSaveToLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 1
        ),
        panel2Single = getComicPanelFromApiAndSaveToLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 2
        ),
        panel3Single = getComicPanelFromApiAndSaveToLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 3
        ),
        panel4Single = getComicPanelFromApiAndSaveToLocalStorage(
            comicNumber = comicNumber,
            panelNumber = 4
        )
    )

    private fun getComicPanelFromApiAndSaveToLocalStorage(
        comicNumber: Int,
        panelNumber: Int
    ): Single<ByteArray> = apiDataSource
        .getComicPanel(
            String.format(
                Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                comicNumber,
                panelNumber
            )
        )
        .map { it.bytes() }
        .map {
            localStorageDataSource.putFile(
                key = String.format(
                    Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                    comicNumber,
                    panelNumber
                ),
                byteArray = it
            )
            it
        }

    private fun joinPanels(
        panel1Single: Single<ByteArray>,
        panel2Single: Single<ByteArray>,
        panel3Single: Single<ByteArray>,
        panel4Single: Single<ByteArray>
    ): Single<ComicPanels> = Single.zip(
        panel1Single,
        panel2Single,
        panel3Single,
        panel4Single
    ) { panel1, panel2, panel3, panel4 ->
        ComicPanels(
            panel1 = panel1,
            panel2 = panel2,
            panel3 = panel3,
            panel4 = panel4
        )
    }
}