package com.iamsteve.domain.view.start

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.BasePresenter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

class StartPresenter : BasePresenter<StartView>() {

    override fun subscribeView(view: StartView) {
        val state = BehaviorSubject.create<StartView.State>()

        state
            .subscribe(view::setState)
            .autoDispose()

        GetComicsOperation()
            .execute()
            .handleError(view.errorHandler)
            .flatMap { comics ->
                var sequentialDownload: Single<Any> = Single.just(Unit)
                comics.forEach { comic ->
                    sequentialDownload = sequentialDownload.flatMap {
                        GetComicPanelsOperation(comic)
                            .execute()
                            .map {
                                state.onNext(
                                    StartView.State(
                                        done = comic.number,
                                        all = comics.size
                                    )
                                )
                            }
                            .handleError(view.errorHandler)
                    }
                }
                sequentialDownload.map { comics }
            }
            .handleError(view.errorHandler)
            .subscribe { comics -> view.navigateToComicGalleryScreen(comics) }
            .autoDispose()
    }
}