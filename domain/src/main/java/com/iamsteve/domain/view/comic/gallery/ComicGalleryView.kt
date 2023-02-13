package com.iamsteve.domain.view.comic.gallery

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.base.Presenter
import com.iamsteve.domain.view.base.View
import io.reactivex.rxjava3.core.Observable

interface ComicGalleryView : View<Presenter<ComicGalleryView>> {
    val comics: List<Comic>
    val pageChangedTrigger: Observable<Int>
    val previousButtonTrigger: Observable<Unit>
    val archiveButtonTrigger: Observable<Unit>
    val nextButtonTrigger: Observable<Unit>
    val comicSelectedInArchiveTrigger: Observable<Comic>
    val currentPosition: Int

    fun setState(state: State)
    fun setPosition(position: Int)
    fun navigateToArchiveScreen(comics: List<Comic>)

    data class State(
        val comics: List<Comic>,
        val previousButtonVisible: Boolean,
        val nextButtonVisible: Boolean
    )
}