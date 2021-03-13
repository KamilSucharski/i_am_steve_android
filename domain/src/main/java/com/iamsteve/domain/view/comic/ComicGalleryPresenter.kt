package com.iamsteve.domain.view.comic

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class ComicGalleryPresenter: Presenter<ComicGalleryContract.View>(), ComicGalleryContract.Presenter {

    override fun subscribe(view: ComicGalleryContract.View) {
        val comicsSubject = BehaviorSubject.create<List<Comic>>()

        comicsSubject
            .subscribe(view::displayComics)
            .addTo(disposables)

        GetComicsOperation()
            .execute()
            .handleError(view.errorHandler)
            .subscribe { comicsSubject.onNext(it) }
            .addTo(disposables)

        view.pageChangedTrigger
            .withLatestFrom(comicsSubject) { position, comics -> Pair(position, comics.lastIndex) }
            .subscribe {
                view.setButtonVisibility(
                    previousButtonVisible = it.first != 0,
                    nextButtonVisible = it.first != it.second
                )
            }
            .addTo(disposables)

        view.previousButtonTrigger
            .subscribe { view.setPosition(view.currentPosition - 1) }
            .addTo(disposables)

        view.archiveButtonTrigger
            .subscribe { view.navigateToArchiveScreen() }
            .addTo(disposables)

        view.nextButtonTrigger
            .subscribe { view.setPosition(view.currentPosition + 1) }
            .addTo(disposables)

        view.comicSelectedInArchiveTrigger
            .withLatestFrom(comicsSubject) { comic, comics -> comics.indexOf(comic) }
            .subscribe { view.setPosition(it) }
            .addTo(disposables)
    }
}