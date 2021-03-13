package com.iamsteve.domain.view.start

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.operation.PreloadComicsOperation
import com.iamsteve.domain.util.Logger
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo

class StartPresenter : Presenter<StartContract.View>(), StartContract.Presenter {

    override fun subscribe(view: StartContract.View) {

        val preloadComics = PreloadComicsOperation().execute()
        val downloadTrigger = view.downloadTrigger.startWith(Unit)

        Observable
            .combineLatest(preloadComics, downloadTrigger) { _, _ -> }
            .flatMap {
                view.setState(StartContract.State.DOWNLOADING_COMIC_LIST)
                GetComicsOperation()
                    .execute()
                    .handleError(view)
            }
            .flatMap { comics ->
                view.setState(StartContract.State.DOWNLOADING_COMIC_PANELS)
                var sequentialDownload: Observable<Any> = Observable.just(Unit)
                comics.forEach { comic ->
                    sequentialDownload = sequentialDownload.flatMap {
                        GetComicPanelsOperation(comic)
                            .execute()
                            .handleError(view)
                    }
                }
                sequentialDownload
            }
            .handleError(view)
            .subscribe {
                view.navigateToComicGalleryScreen()
            }
            .addTo(disposables)
    }
}