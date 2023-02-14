package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.ReactiveSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.inject

class GetComicPanelsOperation(
    private val comic: Comic
) : Operation<Single<ComicPanels>> {

    private val comicRepository by inject<ComicRepository>()
    private val schedulers by inject<ReactiveSchedulers>()

    override fun execute(): Single<ComicPanels> = comicRepository
        .getComicPanels(comicNumber = comic.number)
        .schedule(schedulers)
}