package com.iamsteve.domain.repository

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import io.reactivex.rxjava3.core.Single

interface ComicRepository {
    fun getComics(): Single<List<Comic>>
    fun getComicPanels(comicNumber: Int): Single<ComicPanels>
}