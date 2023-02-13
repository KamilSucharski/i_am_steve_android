package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.rxjava3.core.Single

class GetComicsOperation(
    private val comicRepositoryLocal: ComicRepository.Local,
    private val comicRepositoryRemote: ComicRepository.Remote,
    private val schedulers: RxSchedulers,
    private val logger: Logger
) : Operation<Unit, Single<List<Comic>>> {

    override fun execute(input: Unit): Single<List<Comic>> {
        return getFromApi()
            .onErrorResumeNext {
                logger.error(
                    "Could not get comics.json from the API. Trying local storage.",
                    it
                )
                getFromLocalStorage()
            }
            .onErrorResumeNext {
                logger.error(
                    "Could not get comics.json from the local storage. Trying assets.",
                    it
                )
                getFromAssets()
            }
            .schedule(schedulers)
    }

    private fun getFromApi(): Single<List<Comic>> = comicRepositoryRemote
        .getComics()
        .map {
            comicRepositoryLocal.saveComicsToLocalStorage(it)
            it
        }

    private fun getFromLocalStorage(): Single<List<Comic>> = comicRepositoryLocal
        .getComicsFromLocalStorage()

    private fun getFromAssets(): Single<List<Comic>> = comicRepositoryLocal.getComicsFromAssets()
}