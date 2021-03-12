package com.iamsteve.data.repository

import com.iamsteve.data.api.ComicAPI
import com.iamsteve.data.mapper.ComicMapper
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.map
import io.reactivex.Observable

class ComicRepositoryRemote(
    private val comicAPI: ComicAPI
) : ComicRepository.Remote {

    override fun getComics(): Observable<List<Comic>> {
        return comicAPI
            .getComics()
            .map { it.map(ComicMapper()) }
    }

    override fun getComicPanel(comicNumber: Int, panelNumber: Int): Observable<ByteArray> {
        return comicAPI
            .getComicPanel(String.format(
                Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                comicNumber,
                panelNumber
            ))
            .map { it.bytes() }
    }
}