package com.iamsteve.domain.view.comic.single

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.BasePresenter

class ComicPresenter(
    private val getComicPanelsOperation: GetComicPanelsOperation
) : BasePresenter<ComicContract.View>(), ComicContract.Presenter {

    override fun subscribeView(view: ComicContract.View) {
        getComicPanelsOperation
            .execute(view.comic)
            .map { ComicContract.State(comic = view.comic, comicPanels = it) }
            .handleError(view.errorHandler)
            .subscribe(view::setState)
            .autoDispose()
    }
}