package com.iamsteve.domain.operation

import com.iamsteve.domain.exception.NoComicsException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.repository.AssetRepository
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.abstraction.Serializer
import com.iamsteve.domain.util.extension.catchError
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.Observable
import io.reactivex.Single
import java.io.BufferedReader

class GetComicsOperation(
    private val assetRepositoryLocal: AssetRepository.Local,
    private val comicRepositoryLocal: ComicRepository.Local,
    private val comicRepositoryRemote: ComicRepository.Remote,
    private val schedulers: RxSchedulers,
    private val serializer: Serializer,
    private val logger: Logger
) : Operation<Unit, Single<List<Comic>>> {

    override fun execute(input: Unit): Single<List<Comic>> {
        return getFromAPI()
            .catchError {
                logger.error(
                    "Could not get comics.json from the API. Trying local storage.",
                    it
                )
                getFromLocalStorage()
            }
            .catchError {
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

    private fun getFromAssets(): Single<List<Comic>> = assetRepositoryLocal
        .read(Consts.COMIC_METADATA_FILE_NAME)
        .bufferedReader()
        .use(BufferedReader::readText)
        .let { serializer.deserialize<List<Comic>>(it) }
        ?.let { Single.just(it) }
        ?: Single.error(NoComicsException())
}