package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.abstraction.Logger
import com.iamsteve.domain.util.abstraction.Operation
import com.iamsteve.domain.util.abstraction.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.rxjava3.core.Single

class GetComicPanelsOperation(
    private val schedulers: RxSchedulers,
    private val comicRepositoryLocal: ComicRepository.Local,
    private val comicRepositoryRemote: ComicRepository.Remote,
    private val logger: Logger
) : Operation<Comic, Single<ComicPanels>> {

    override fun execute(input: Comic): Single<ComicPanels> {
        return getFromAssets(input.number)
            .onErrorResumeNext {
                logger.error(
                    "Could not get comic panels from the assets. Trying local storage.",
                    it
                )
                getFromLocalStorage(input.number)
            }
            .onErrorResumeNext {
                logger.error(
                    "Could not get comic panels from the local storage. Trying API.",
                    it
                )
                getFromApi(input.number)
            }
            .schedule(schedulers)
    }

    private fun getFromAssets(comicNumber: Int): Single<ComicPanels> = joinPanels(
        panel1Single = getPanelFromAssets(comicNumber = comicNumber, panelNumber = 1),
        panel2Single = getPanelFromAssets(comicNumber = comicNumber, panelNumber = 2),
        panel3Single = getPanelFromAssets(comicNumber = comicNumber, panelNumber = 3),
        panel4Single = getPanelFromAssets(comicNumber = comicNumber, panelNumber = 4)
    )

    private fun getPanelFromAssets(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return comicRepositoryLocal
            .getComicPanelFromAssets(
                comicNumber = comicNumber,
                panelNumber = panelNumber
            )
            .map {
                comicRepositoryLocal.removeComicPanelFromLocalStorage(
                    comicNumber = comicNumber,
                    panelNumber = panelNumber
                )
                it
            }
    }

    private fun getFromLocalStorage(comicNumber: Int): Single<ComicPanels> = joinPanels(
        panel1Single = getPanelFromLocalStorage(comicNumber = comicNumber, panelNumber = 1),
        panel2Single = getPanelFromLocalStorage(comicNumber = comicNumber, panelNumber = 2),
        panel3Single = getPanelFromLocalStorage(comicNumber = comicNumber, panelNumber = 3),
        panel4Single = getPanelFromLocalStorage(comicNumber = comicNumber, panelNumber = 4)
    )

    private fun getPanelFromLocalStorage(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return comicRepositoryLocal.getComicPanelFromLocalStorage(
            comicNumber = comicNumber,
            panelNumber = panelNumber
        )
    }

    private fun getFromApi(comicNumber: Int): Single<ComicPanels> = joinPanels(
        panel1Single = getPanelFromAPI(comicNumber = comicNumber, panelNumber = 1),
        panel2Single = getPanelFromAPI(comicNumber = comicNumber, panelNumber = 2),
        panel3Single = getPanelFromAPI(comicNumber = comicNumber, panelNumber = 3),
        panel4Single = getPanelFromAPI(comicNumber = comicNumber, panelNumber = 4)
    )

    private fun getPanelFromAPI(comicNumber: Int, panelNumber: Int): Single<ByteArray> {
        return comicRepositoryRemote
            .getComicPanel(
                comicNumber = comicNumber,
                panelNumber = panelNumber
            )
            .map {
                comicRepositoryLocal.saveComicPanelToLocalStorage(
                    comicNumber = comicNumber,
                    panelNumber = panelNumber,
                    byteArray = it
                )
            }
    }

    private fun joinPanels(
        panel1Single: Single<ByteArray>,
        panel2Single: Single<ByteArray>,
        panel3Single: Single<ByteArray>,
        panel4Single: Single<ByteArray>
    ): Single<ComicPanels> = Single.zip(
        panel1Single,
        panel2Single,
        panel3Single,
        panel4Single
    ) { panel1, panel2, panel3, panel4 ->
        ComicPanels(
            panel1 = panel1,
            panel2 = panel2,
            panel3 = panel3,
            panel4 = panel4
        )
    }
}