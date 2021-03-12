package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Operation
import com.iamsteve.domain.util.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import com.iamsteve.domain.util.Logger
import io.reactivex.Observable
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetComicsOperation : Operation<Observable<List<Comic>>>, KoinComponent {

    private val schedulers by inject<RxSchedulers>()
    private val comicRepositoryLocal by inject<ComicRepository.Local>()
    private val comicRepositoryRemote by inject<ComicRepository.Remote>()
    private val logger by inject<Logger>()

    override fun execute(): Observable<List<Comic>> {
        return comicRepositoryRemote
            .getComics()
            .map {
                comicRepositoryLocal.saveComics(ArrayList(it))
                it
            }
            .onErrorReturn {
                logger.error("Error getting comics", it)
                val existingComics = comicRepositoryLocal.loadComics()
                if (existingComics.isPresent) {
                    existingComics.get()
                } else {
                    emptyList()
                }
            }
            .schedule(schedulers)
    }
}