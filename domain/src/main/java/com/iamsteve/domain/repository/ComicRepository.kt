package com.iamsteve.domain.repository

import com.iamsteve.domain.model.Comic
import io.reactivex.rxjava3.core.Single
import java.io.File

interface ComicRepository {

    interface Local {
        fun getComicsFromAssets(): Single<List<Comic>>
        fun getComicsFromLocalStorage(): Single<List<Comic>>
        fun saveComicsToLocalStorage(comics: List<Comic>)

        fun getComicPanelFromAssets(comicNumber: Int, panelNumber: Int): Single<ByteArray>
        fun getComicPanelFromLocalStorage(comicNumber: Int, panelNumber: Int): Single<ByteArray>
        fun saveComicPanelToLocalStorage(comicNumber: Int, panelNumber: Int, byteArray: ByteArray): ByteArray
        fun removeComicPanelFromLocalStorage(comicNumber: Int, panelNumber: Int)
    }

    interface Remote {
        fun getComics(): Single<List<Comic>>
        fun getComicPanel(comicNumber: Int, panelNumber: Int): Single<ByteArray>
    }
}