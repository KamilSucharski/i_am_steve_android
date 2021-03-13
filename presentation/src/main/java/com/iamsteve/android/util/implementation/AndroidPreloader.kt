package com.iamsteve.android.util.implementation

import android.content.res.AssetManager
import com.google.gson.Gson
import com.iamsteve.android.util.extension.fromJson
import com.iamsteve.data.util.LocalStorage
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.Preloader
import java.io.BufferedReader

class AndroidPreloader(
    private val assetManager: AssetManager,
    private val localStorage: LocalStorage,
    private val gson: Gson
) : Preloader {

    override fun preload() {
        if (localStorage.getBoolean(Consts.KEY_PRELOAD_COMPLETED) == true) {
            return
        }

        val comics: ArrayList<Comic> = assetManager
            .open(Consts.COMIC_METADATA_FILE_NAME)
            .bufferedReader()
            .use(BufferedReader::readText)
            .let { gson.fromJson(it) }

        comics.forEach { comic ->
            for (panelNumber in 1..4) {
                val fileName = String.format(
                    Consts.COMIC_PANEL_FILE_NAME_FORMAT,
                    comic.number,
                    panelNumber
                )
                assetManager
                    .open(fileName)
                    .readBytes()
                    .let { localStorage.putFile(fileName, it) }
            }
        }

        localStorage.putSerializable(Consts.KEY_COMIC_LIST, comics)
        localStorage.putBoolean(Consts.KEY_PRELOAD_COMPLETED, true)
    }
}