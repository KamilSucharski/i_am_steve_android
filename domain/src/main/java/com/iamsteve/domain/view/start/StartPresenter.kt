package com.iamsteve.domain.view.start

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.Single
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

class StartPresenter(
    private val getComicsOperation: GetComicsOperation,
    private val getComicPanelsOperation: GetComicPanelsOperation
) : Presenter<StartContract.View>(), StartContract.Presenter {

    override fun subscribe(view: StartContract.View) {
        getComicsOperation
            .execute(Unit)
            .handleError(view.errorHandler)
            .flatMap { comics ->
                var sequentialDownload: Single<Any> = Single.just(Unit)
                comics.forEach { comic ->
                    sequentialDownload = sequentialDownload.flatMap {
                        getComicPanelsOperation
                            .execute(comic)
                            .map { view.setProgress(comic.number, comics.size) }
                            .handleError(view.errorHandler)
                    }
                }
                sequentialDownload.map { comics }
            }
            .handleError(view.errorHandler)
            .subscribeBy { view.navigateToComicGalleryScreen(it) }
            .addTo(disposables)
    }
}