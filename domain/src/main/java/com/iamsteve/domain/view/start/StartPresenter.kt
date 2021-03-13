package com.iamsteve.domain.view.start

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.operation.PreloadComicsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo

class StartPresenter : Presenter<StartContract.View>(), StartContract.Presenter {

    override fun subscribe(view: StartContract.View) {
        PreloadComicsOperation()
            .execute()
            .flatMap {
                GetComicsOperation()
                    .execute()
                    .handleError(view.errorHandler)
            }
            .flatMap { comics ->
                var sequentialDownload: Observable<Any> = Observable.just(Unit)
                comics.forEach { comic ->
                    sequentialDownload = sequentialDownload.flatMap {
                        GetComicPanelsOperation(comic)
                            .execute()
                            .map { view.setProgress(comic.number, comics.size) }
                            .handleError(view.errorHandler)
                    }
                }
                sequentialDownload
            }
            .handleError(view.errorHandler)
            .subscribe { view.navigateToComicGalleryScreen() }
            .addTo(disposables)
    }
}