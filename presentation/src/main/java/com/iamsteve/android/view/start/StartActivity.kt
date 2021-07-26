package com.iamsteve.android.view.start

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityStartBinding
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.comic.gallery.ComicGalleryActivity
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.view.start.StartContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class StartActivity : BaseActivity<StartContract.View, StartContract.Presenter, ActivityStartBinding>(
    layoutResource = R.layout.activity_start
), StartContract.View {

    override val presenter: StartContract.Presenter by inject()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ this }) }

    override fun setState(state: StartContract.State) {
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