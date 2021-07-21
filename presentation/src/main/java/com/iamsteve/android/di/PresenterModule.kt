package com.iamsteve.android.di

import com.iamsteve.domain.view.archive.ArchiveContract
import com.iamsteve.domain.view.archive.ArchivePresenter
import com.iamsteve.domain.view.comic.single.ComicContract
import com.iamsteve.domain.view.comic.gallery.ComicGalleryContract
import com.iamsteve.domain.view.comic.gallery.ComicGalleryPresenter
import com.iamsteve.domain.view.comic.single.ComicPresenter
import com.iamsteve.domain.view.start.StartContract
import com.iamsteve.domain.view.start.StartPresenter
import org.koin.dsl.module

val presenterModule = module {
    // Start
    factory<StartContract.Presenter> { StartPresenter(get(), get()) }

    // Comic
    factory<ComicGalleryContract.Presenter> { ComicGalleryPresenter() }
    factory<ComicContract.Presenter> { ComicPresenter(get()) }

    // Archive
    factory<ArchiveContract.Presenter> { ArchivePresenter() }
}