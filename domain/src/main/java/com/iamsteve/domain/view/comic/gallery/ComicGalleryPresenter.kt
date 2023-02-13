package com.iamsteve.domain.view.comic.gallery

import com.iamsteve.domain.view.base.BasePresenter
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ComicGalleryPresenter : BasePresenter<ComicGalleryContract.View>(), ComicGalleryContract.Presenter {

    override fun subscribeView(view: ComicGalleryContract.View) {
        val state = BehaviorSubject.createDefault(ComicGalleryContract.State(
            comics = view.comics,
            previousButtonVisible = true,
            nextButtonVisible = false
        ))

        state
            .subscribe(view::setState)
            .autoDispose()

        view.pageChangedTrigger
            .withLatestFrom(state) { position, previousState -> previousState.copy(
                previousButtonVisible = position != 0,
                nextButtonVisible = position != previousState.comics.lastIndex
            )}
            .subscribe(state::onNext)
            .autoDispose()

        view.previousButtonTrigger
            .map { view.currentPosition - 1 }
            .subscribe(view::setPosition)
            .autoDispose()

        view.archiveButtonTrigger
            .withLatestFrom(state) { _, stateValue -> stateValue.comics }
            .subscribe(view::navigateToArchiveScreen)
            .autoDispose()

        view.nextButtonTrigger
            .map { view.currentPosition + 1 }
            .subscribe(view::setPosition)
            .autoDispose()

        view.comicSelectedInArchiveTrigger
            .withLatestFrom(state) { comic, stateValue -> stateValue.comics.indexOf(comic) }
            .subscribe(view::setPosition)
            .autoDispose()
    }
}