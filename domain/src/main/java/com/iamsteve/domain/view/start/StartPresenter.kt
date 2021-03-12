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
            .flatMap { comics ->
                view.setState(StartContract.State.DOWNLOADING_COMICS)
                comics
                    .map { GetComicPanelsOperation(it).execute() }
                    .toList()
                    .let { Observable.zip(it) { } }
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