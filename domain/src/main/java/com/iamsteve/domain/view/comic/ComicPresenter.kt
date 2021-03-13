package com.iamsteve.domain.view.comic

import com.iamsteve.domain.operation.GetComicPanelsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo

class ComicPresenter : Presenter<ComicContract.View>(), ComicContract.Presenter {

    override fun subscribe(view: ComicContract.View) {
        GetComicPanelsOperation(comic = view.comic)
            .execute()
            .map { ComicContract.Data(comic = view.comic, comicPanels = it) }
            .handleError(view.errorHandler)
            .subscribe(view::setData)
            .addTo(disposables)
    }
}