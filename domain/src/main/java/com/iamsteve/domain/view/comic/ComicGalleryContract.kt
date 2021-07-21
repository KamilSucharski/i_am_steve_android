package com.iamsteve.domain.view.comic

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView
import io.reactivex.Observable

interface ComicGalleryContract {
    interface View : BaseView<Presenter> {
        val comics: List<Comic>
        val pageChangedTrigger: Observable<Int>
        val previousButtonTrigger: Observable<Unit>
        val archiveButtonTrigger: Observable<Unit>
        val nextButtonTrigger: Observable<Unit>
        val comicSelectedInArchiveTrigger: Observable<Comic>
        val currentPosition: Int

        fun displayComics(comics: List<Comic>)
        fun setPosition(position: Int)
        fun setButtonVisibility(previousButtonVisible: Boolean, nextButtonVisible: Boolean)
        fun navigateToArchiveScreen(comics: List<Comic>)
    }

    interface Presenter : BasePresenter<View>
}