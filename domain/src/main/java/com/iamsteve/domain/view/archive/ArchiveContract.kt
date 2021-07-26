package com.iamsteve.domain.view.archive

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView
import io.reactivex.Observable

interface ArchiveContract {
    interface View : BaseView<Presenter> {
        val comics: List<Comic>
        val comicTrigger: Observable<Comic>

        fun setState(state: State)
        fun navigateToComic(comic: Comic)
    }

    interface Presenter : BasePresenter<View>

    data class State(
        val comics: List<Comic>
    )
}