package com.iamsteve.domain.view.comic

import com.iamsteve.domain.view.base.Presenter

class ComicPresenter: Presenter<ComicContract.View>(), ComicContract.Presenter {

    override fun subscribe(view: ComicContract.View) {
    }
}