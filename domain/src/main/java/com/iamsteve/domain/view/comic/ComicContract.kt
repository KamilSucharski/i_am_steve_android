package com.iamsteve.domain.view.comic

import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.model.ComicPanels
import com.iamsteve.domain.util.error.ErrorHandling
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView

interface ComicContract {
    interface View: BaseView<Presenter>, ErrorHandling {
        val comic: Comic

        fun setData(data: Data)
    }

    interface Presenter: BasePresenter<View>

    data class Data(
        val comic: Comic,
        val comicPanels: ComicPanels
    )
}