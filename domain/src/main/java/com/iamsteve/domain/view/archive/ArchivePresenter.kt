package com.iamsteve.domain.view.archive

import com.iamsteve.domain.view.base.Presenter
import io.reactivex.rxkotlin.addTo

class ArchivePresenter : Presenter<ArchiveContract.View>(), ArchiveContract.Presenter {

    override fun subscribe(view: ArchiveContract.View) {
        view.setData(view.comics)

        view.comicTrigger
            .subscribe(view::navigateToComic)
            .addTo(disposables)
    }
}