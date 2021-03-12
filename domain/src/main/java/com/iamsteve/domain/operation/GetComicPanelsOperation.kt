package com.iamsteve.domain.operation

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.repository.ComicRepository
import com.iamsteve.domain.util.Operation
import com.iamsteve.domain.util.RxSchedulers
import com.iamsteve.domain.util.extension.schedule
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class GetComicPanelsOperation(
    private val comic: Comic
) : Operation<Observable<ComicPanels>>, KoinComponent {

    private val schedulers by inject<RxSchedulers>()
    private val comicRepositoryLocal by inject<ComicRepository.Local>()
    private val comicRepositoryRemote by inject<ComicRepository.Remote>()

    override fun execute(): Observable<ComicPanels> {
        val panelObservables = mutableListOf<Observable<File>>()
        for (panelNumber in 1..4) {
            comicRepositoryRemote
                .getComicPanel(
                    comicNumber = comic.number,
                    panelNumber = panelNumber
                )
                .map {
                    comicRepositoryLocal.saveComicPanel(
                        comicNumber = comic.number,
                        panelNumber = panelNumber,
                        byteArray = it
                    )
                }
                .schedule(schedulers)
                .let { panelObservables.add(it) }
        }

        return Observables.zip(
            panelObservables[0],
            panelObservables[1],
            panelObservables[2],
            panelObservables[3]
        ) { panel1, panel2, panel3, panel4 ->
            ComicPanels(
                panel1 = panel1,
                panel2 = panel2,
                panel3 = panel3,
                panel4 = panel4
            )
        }
    }
}