package com.iamsteve.domain.view.archive

import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject

class ArchivePresenter : Presenter<ArchiveContract.View>(), ArchiveContract.Presenter {

    override fun subscribe(view: ArchiveContract.View) {
        val state = BehaviorSubject.createDefault(ArchiveContract.State(comics = view.comics))

        state
            .subscribe(view::setState)
            .addTo(disposables)

        view.comicTrigger
            .subscribe(view::navigateToComic)
            .addTo(disposables)
    }
}