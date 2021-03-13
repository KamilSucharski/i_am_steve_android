package com.iamsteve.domain.view.archive

import com.iamsteve.domain.operation.GetComicsOperation
import com.iamsteve.domain.util.extension.handleError
import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo

class ArchivePresenter: Presenter<ArchiveContract.View>(), ArchiveContract.Presenter {

    override fun subscribe(view: ArchiveContract.View) {
        GetComicsOperation()
            .execute()
            .handleError(view.errorHandler)
            .subscribe(view::setData)
            .addTo(disposables)

        view.comicTrigger
            .subscribe(view::navigateToComic)
            .addTo(disposables)
    }
}