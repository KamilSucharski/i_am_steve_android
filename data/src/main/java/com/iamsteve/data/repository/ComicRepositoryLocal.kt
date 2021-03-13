package com.iamsteve.data.repository

import com.iamsteve.data.util.LocalStorage
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import java.io.File

class ComicRepositoryLocal(
    private val localStorage: LocalStorage
) : ComicRepository.Local {

    override fun saveComics(comics: ArrayList<Comic>) {
        localStorage.putSerializable(
            key = Consts.KEY_COMIC_LIST,
            serializable = comics
        )
    }

    override fun loadComics(): List<Comic>? {
        return localStorage.getSerializable(
            key = Consts.KEY_COMIC_LIST,
            type = ArrayList::class.java
        )
    }

    override fun saveComicPanel(comicNumber: Int, panelNumber: Int, byteArray: ByteArray): File {
       return localStorage.putFile(
           key = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber),
           byteArray = byteArray
       )
    }

    override fun loadComicPanel(comicNumber: Int, panelNumber: Int): File? {
        return localStorage.getFile(
            key = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber)
        )
    }

    override fun containsComic(comicNumber: Int): Boolean {
        for (panelNumber in 1..4) {
            val fileName = String.format(Consts.COMIC_PANEL_FILE_NAME_FORMAT, comicNumber, panelNumber)
            if (!localStorage.containsFile(fileName)) {
                return false
            }
        }
        return true
    }
}