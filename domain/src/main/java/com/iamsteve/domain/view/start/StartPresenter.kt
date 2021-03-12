package com.iamsteve.domain.view.start

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo

class StartPresenter : Presenter<StartContract.View>(), StartContract.Presenter {

    override fun subscribe(view: StartContract.View) {
        view.setState(StartContract.State.DOWNLOADING_COMICS_METADATA)

        view.downloadTrigger
            .startWith(Unit)
            .flatMap { GetComicsOperation().execute() }
            .map {
                view.setState(StartContract.State.DOWNLOADING_COMICS)
                it
            }
            .flatMap { comics ->
                var sequentialDownload: Observable<Any> = Observable.just(Unit)
                comics.forEach { comic ->
                    sequentialDownload = sequentialDownload.flatMap {
                        GetComicPanelsOperation(comic).execute()
                    }
                }
                sequentialDownload
            }
            .onErrorReturn {
                view.setState(StartContract.State.CONNECTION_ERROR)
            }
            .subscribe {
                view.navigateToComicGalleryScreen()
            }
            .addTo(disposables)
    }
}