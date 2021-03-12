package com.iamsteve.android.view.comic

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityComicGalleryBinding
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.domain.view.comic.ComicGalleryContract
import org.koin.android.ext.android.inject

class ComicGalleryActivity : BaseActivity<ComicGalleryContract.View, ComicGalleryContract.Presenter, ActivityComicGalleryBinding>(
    layoutResource = R.layout.activity_comic_gallery
), ComicGalleryContract.View {

    override val presenter: ComicGalleryContract.Presenter by inject()
}