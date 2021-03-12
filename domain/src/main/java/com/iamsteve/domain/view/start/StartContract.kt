package com.iamsteve.domain.view.start

import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView
import io.reactivex.Observable

interface StartContract {
    interface View: BaseView<Presenter> {
        val downloadTrigger: Observable<Unit>

        fun setState(state: State)
        fun navigateToComicGalleryScreen()
    }

    interface Presenter: BasePresenter<View>

    enum class State {
        DOWNLOADING_COMICS_METADATA,
        DOWNLOADING_COMICS,
        CONNECTION_ERROR
    }
}