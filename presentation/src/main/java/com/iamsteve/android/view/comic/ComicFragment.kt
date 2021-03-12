package com.iamsteve.android.view.comic

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityComicGalleryBinding
import com.iamsteve.android.databinding.FragmentComicBinding
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.domain.view.comic.ComicContract
import com.iamsteve.domain.view.comic.ComicGalleryContract
import org.koin.android.ext.android.inject

class ComicFragment : BaseActivity<ComicContract.View, ComicContract.Presenter, FragmentComicBinding>(
    layoutResource = R.layout.fragment_comic
), ComicContract.View {

    override val presenter: ComicContract.Presenter by inject()
}