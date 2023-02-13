package com.iamsteve.domain.util.di

import com.iamsteve.domain.view.archive.ArchivePresenter
import com.iamsteve.domain.view.comic.gallery.ComicGalleryPresenter
import com.iamsteve.domain.view.comic.single.ComicPresenter
import com.iamsteve.domain.view.start.StartPresenter
import org.koin.dsl.module

val presenterModule = module {
    // Start
    factory { StartPresenter() }

    // Comic
    factory { ComicGalleryPresenter() }
    factory { ComicPresenter() }

    // Archive
    factory { ArchivePresenter() }
}