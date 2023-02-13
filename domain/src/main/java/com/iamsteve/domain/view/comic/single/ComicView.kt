package com.iamsteve.domain.view.comic.single

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.util.error.ErrorHandling
import com.iamsteve.domain.util.Presenter
import com.iamsteve.domain.util.View

interface ComicView : View<Presenter<ComicView>>, ErrorHandling {
    val comic: Comic

    fun setState(state: State)

    data class State(
        val comic: Comic,
        val comicPanels: ComicPanels
    )
}