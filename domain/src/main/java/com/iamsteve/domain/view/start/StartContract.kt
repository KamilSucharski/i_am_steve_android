package com.iamsteve.domain.view.start

import com.iamsteve.domain.util.error.ErrorHandler
import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView
import io.reactivex.Observable

interface StartContract {
    interface View: BaseView<Presenter>, ErrorHandler {
        val downloadTrigger: Observable<Unit>

        fun setState(state: State)
        fun navigateToComicGalleryScreen()
    }

    interface Presenter: BasePresenter<View>

    enum class State {
        PRELOADING_COMICS,
        DOWNLOADING_COMIC_LIST,
        DOWNLOADING_COMIC_PANELS,
        ERROR
    }
}