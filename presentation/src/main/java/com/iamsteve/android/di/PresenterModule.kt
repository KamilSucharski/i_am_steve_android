package com.iamsteve.android.di

import com.iamsteve.domain.view.archive.ArchivePresenter
import com.iamsteve.domain.view.comic.gallery.ComicGalleryPresenter
import com.iamsteve.domain.view.comic.single.ComicPresenter
import com.iamsteve.domain.view.start.StartPresenter
import org.koin.dsl.module

val presenterModule = module {
    // Start
    factory { StartPresenter(get(), get()) }

    // Comic
    factory { ComicGalleryPresenter() }
    factory { ComicPresenter(get()) }

    // Archive
    factory { ArchivePresenter() }
}