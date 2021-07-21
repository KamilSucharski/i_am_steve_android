package com.iamsteve.data.repository

import com.iamsteve.data.api.ComicApi
import com.iamsteve.data.mapper.ComicMapper
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.map
import io.reactivex.Single

class ComicRepositoryRemote(
    private val comicApi: ComicApi,
    private val comicMapper: ComicMapper
) : ComicRepository.Remote {

    override fun getComics(): Single<List<Comic>> {
        return comicApi
            .getComics()
            .map { it.map(comicMapper) }
    }

    override fun getComicPanel(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return comicApi
            .getComicPanel(
                String.format(
                    Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                    comicNumber,
                    panelNumber
                )
            )
            .map { it.bytes() }
    }
}