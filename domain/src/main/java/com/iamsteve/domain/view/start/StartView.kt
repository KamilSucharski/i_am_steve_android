package com.iamsteve.domain.view.start

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.error.ErrorHandling
import com.iamsteve.domain.util.Presenter
import com.iamsteve.domain.util.View

interface StartView : View<Presenter<StartView>>, ErrorHandling {
    fun setState(state: State)
    fun navigateToComicGalleryScreen(comics: List<Comic>)

    data class State(
        val done: Int,
        val all: Int
    )
}