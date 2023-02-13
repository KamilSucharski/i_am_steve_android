package com.iamsteve.domain.view.archive

import com.iamsteve.domain.util.BasePresenter
import io.reactivex.rxjava3.subjects.BehaviorSubject

class ArchivePresenter : BasePresenter<ArchiveView>(){

    override fun subscribeView(view: ArchiveView) {
        val state = BehaviorSubject.createDefault(ArchiveView.State(comics = view.comics))

        state
            .subscribe(view::setState)
            .autoDispose()

        view.comicTrigger
            .subscribe(view::navigateToComic)
            .autoDispose()
    }
}