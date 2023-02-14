package com.iamsteve.android.view.start

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityStartBinding
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.util.BaseActivity
import com.iamsteve.android.view.comic.gallery.ComicGalleryActivity
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.start.StartPresenter
import com.iamsteve.domain.view.start.StartView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class StartActivity : BaseActivity<StartView, ActivityStartBinding>(
    layoutResource = R.layout.activity_start
), StartView {

    override val presenter by inject<StartPresenter>()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ this }) }

    override fun setState(state: StartView.State) {
        binding.bodyTextView.text = getString(
            R.string.start_body_with_progress,
            state.done,
            state.all
        )
    }

    override fun navigateToComicGalleryScreen(comics: List<Comic>) {
        ComicGalleryActivity.start(this, comics)
        finish()
    }
}