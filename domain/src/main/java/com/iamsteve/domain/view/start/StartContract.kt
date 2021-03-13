package com.iamsteve.domain.view.start

import com.iamsteve.domain.util.error.ErrorHandler
import com.iamsteve.domain.util.error.ErrorHandling
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView
import io.reactivex.Observable

interface StartContract {
    interface View: BaseView<Presenter>, ErrorHandling {
        fun setProgress(done: Int, all: Int)
        fun navigateToComicGalleryScreen()
    }

    interface Presenter: BasePresenter<View>
}