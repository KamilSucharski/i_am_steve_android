package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.inject

class GetComicsOperation : Operation<Single<List<Comic>>> {

    private val comicRepository by inject<ComicRepository>()
    private val schedulers by inject<RxSchedulers>()

    override fun execute(): Single<List<Comic>> = comicRepository
        .getComics()
        .schedule(schedulers)
}