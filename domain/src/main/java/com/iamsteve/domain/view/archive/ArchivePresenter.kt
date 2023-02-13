package com.iamsteve.domain.view.archive

import com.iamsteve.domain.view.base.BasePresenter
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ArchivePresenter : BasePresenter<ArchiveContract.View>(), ArchiveContract.Presenter {

    override fun subscribeView(view: ArchiveContract.View) {
        val state = BehaviorSubject.createDefault(ArchiveContract.State(comics = view.comics))

        state
            .subscribe(view::setState)
            .autoDispose()

        view.comicTrigger
            .subscribe(view::navigateToComic)
            .autoDispose()
    }
}