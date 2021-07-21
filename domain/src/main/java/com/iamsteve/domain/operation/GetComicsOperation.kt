package com.iamsteve.domain.operation

import com.iamsteve.domain.exception.NoComicsException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.AssetRepository
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.Single

class GetComicsOperation(
    private val assetRepositoryLocal: AssetRepository.Local,
    private val comicRepositoryLocal: ComicRepository.Local,
    private val comicRepositoryRemote: ComicRepository.Remote,
    private val schedulers: RxSchedulers,
    private val logger: Logger
) : Operation<Unit, Single<List<Comic>>> {

    override fun execute(input: Unit): Single<List<Comic>> {
        return getFromAPI()
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

    private fun getFromAPI(): Single<List<Comic>> = comicRepositoryRemote
        .getComics()
        .map {
            comicRepositoryLocal.saveComics(ArrayList(it))
            it
        }

    private fun getFromLocalStorage(): Single<List<Comic>> = comicRepositoryLocal
        .loadComics()
        ?.let { Single.just(it) }
        ?: Single.error(NoComicsException())

    private fun getFromAssets(): Single<List<Comic>> = assetRepositoryLocal.getComics()
}