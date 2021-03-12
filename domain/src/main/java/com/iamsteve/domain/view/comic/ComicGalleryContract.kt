package com.iamsteve.domain.view.comic

import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView

interface ComicGalleryContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View>
}