package com.iamsteve.domain.view.archive

import com.iamsteve.domain.view.base.BasePresenter
import com.iamsteve.domain.view.base.BaseView

interface ArchiveContract {
    interface View: BaseView<Presenter> {

    }

    interface Presenter: BasePresenter<View>
}