package com.iamsteve.domain.view.start

import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView

interface StartContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View>
}