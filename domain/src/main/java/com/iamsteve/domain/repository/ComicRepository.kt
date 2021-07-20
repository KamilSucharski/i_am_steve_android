package com.iamsteve.domain.repository

import com.iamsteve.domain.model.Comic
import io.reactivex.Observable
import io.reactivex.Single
import java.io.File

interface ComicRepository {

    interface Local {
        fun saveComics(comics: ArrayList<Comic>)
        fun loadComics(): List<Comic>?
        fun saveComicPanel(comicNumber: Int, panelNumber: Int, byteArray: ByteArray): File
        fun loadComicPanel(comicNumber: Int, panelNumber: Int): File?
    }

    interface Remote {
        fun getComics(): Single<List<Comic>>
        fun getComicPanel(comicNumber: Int, panelNumber: Int): Single<ByteArray>
    }
}