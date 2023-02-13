package com.iamsteve.domain.view.archive

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.base.Presenter
import com.iamsteve.domain.view.base.View
import io.reactivex.rxjava3.core.Observable

interface ArchiveView : View<Presenter<ArchiveView>> {
    val comics: List<Comic>
    val comicTrigger: Observable<Comic>

    fun setState(state: State)
    fun navigateToComic(comic: Comic)

    data class State(
        val comics: List<Comic>
    )
}