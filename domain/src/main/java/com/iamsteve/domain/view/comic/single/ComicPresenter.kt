package com.iamsteve.domain.view.comic.single

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.BasePresenter

class ComicPresenter : BasePresenter<ComicView>() {

    override fun subscribeView(view: ComicView) {
        GetComicPanelsOperation(view.comic)
            .execute()
            .map { ComicView.State(comic = view.comic, comicPanels = it) }
            .handleError(view.errorHandler)
            .subscribe(view::setState)
            .autoDispose()
    }
}