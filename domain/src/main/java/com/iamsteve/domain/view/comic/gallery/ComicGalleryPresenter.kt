package com.iamsteve.domain.view.comic.gallery

import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class ComicGalleryPresenter : Presenter<ComicGalleryContract.View>(), ComicGalleryContract.Presenter {

    override fun subscribe(view: ComicGalleryContract.View) {
        val state = BehaviorSubject.createDefault(ComicGalleryContract.State(
            comics = view.comics,
            previousButtonVisible = true,
            nextButtonVisible = false
        ))

        state
            .subscribe(view::setState)
            .addTo(disposables)

        view.pageChangedTrigger
            .withLatestFrom(state) { position, previousState -> previousState.copy(
                previousButtonVisible = position != 0,
                nextButtonVisible = position != previousState.comics.lastIndex
            )}
            .subscribe(state::onNext)
            .addTo(disposables)

        view.previousButtonTrigger
            .map { view.currentPosition - 1 }
            .subscribe(view::setPosition)
            .addTo(disposables)

        view.archiveButtonTrigger
            .withLatestFrom(state) { _, stateValue -> stateValue.comics }
            .subscribe(view::navigateToArchiveScreen)
            .addTo(disposables)

        view.nextButtonTrigger
            .map { view.currentPosition + 1 }
            .subscribe(view::setPosition)
            .addTo(disposables)

        view.comicSelectedInArchiveTrigger
            .withLatestFrom(state) { comic, stateValue -> stateValue.comics.indexOf(comic) }
            .subscribe(view::setPosition)
            .addTo(disposables)
    }
}