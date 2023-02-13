package com.iamsteve.domain.view.start

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.error.ErrorHandling
import com.iamsteve.domain.view.base.BaseView

interface StartContract {
    interface View : BaseView<Presenter>, ErrorHandling {
        fun setState(state: State)
        fun navigateToComicGalleryScreen(comics: List<Comic>)
    }

    interface Presenter : com.iamsteve.domain.view.base.Presenter<View>

    data class State(
        val done: Int,
        val all: Int
    )
}