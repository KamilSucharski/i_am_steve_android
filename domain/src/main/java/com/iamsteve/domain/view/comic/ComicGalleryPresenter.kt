package com.iamsteve.domain.view.comic

import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class ComicGalleryPresenter : Presenter<ComicGalleryContract.View>(), ComicGalleryContract.Presenter {

    override fun subscribe(view: ComicGalleryContract.View) {
        val comics = BehaviorSubject.createDefault(view.comics)

        comics
            .subscribe(view::displayComics)
            .addTo(disposables)

        view.pageChangedTrigger
            .withLatestFrom(comics) { position, comicsValue ->
                Pair(position, comicsValue.lastIndex)
            }
            .subscribe {
                view.setButtonVisibility(
                    previousButtonVisible = it.first != 0,
                    nextButtonVisible = it.first != it.second
                )
            }
            .addTo(disposables)

        view.previousButtonTrigger
            .map { view.currentPosition - 1 }
            .subscribe(view::setPosition)
            .addTo(disposables)

        view.archiveButtonTrigger
            .withLatestFrom(comics) { _, comicsValue -> comicsValue }
            .subscribe(view::navigateToArchiveScreen)
            .addTo(disposables)

        view.nextButtonTrigger
            .map { view.currentPosition + 1 }
            .subscribe(view::setPosition)
            .addTo(disposables)

        view.comicSelectedInArchiveTrigger
            .withLatestFrom(comics) { comic, comicsValue -> comicsValue.indexOf(comic) }
            .subscribe(view::setPosition)
            .addTo(disposables)
    }
}