package com.iamsteve.android.di

import com.iamsteve.domain.view.archive.ArchiveContract
import com.iamsteve.domain.view.archive.ArchivePresenter
import com.iamsteve.domain.view.comic.ComicContract
import com.iamsteve.domain.view.comic.ComicGalleryContract
import com.iamsteve.domain.view.comic.ComicGalleryPresenter
import com.iamsteve.domain.view.comic.ComicPresenter
import com.iamsteve.domain.view.start.StartContract
import com.iamsteve.domain.view.start.StartPresenter
import org.koin.dsl.module

val presenterModule = module {
    // Start
    factory<StartContract.Presenter> { StartPresenter(get()) }

    // Comic
    factory<ComicGalleryContract.Presenter> { ComicGalleryPresenter() }
    factory<ComicContract.Presenter> { ComicPresenter() }

    // Archive
    factory<ArchiveContract.Presenter> { ArchivePresenter() }
}