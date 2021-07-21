package com.iamsteve.domain.repository

import com.iamsteve.domain.model.Comic
import io.reactivex.Single

interface AssetRepository {

    interface Local {
        fun getComics(): Single<List<Comic>>
        fun getComicPanel(comicNumber: Int, panelNumber: Int): Single<ByteArray>
    }
}