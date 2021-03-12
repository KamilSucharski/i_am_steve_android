package com.iamsteve.android.view.start

import android.widget.Toast
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityStartBinding
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.start.mapper.StartStateToBodyTextResourceMapper
import com.iamsteve.domain.util.map
import com.iamsteve.domain.view.start.StartContract
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import org.koin.android.ext.android.inject

class StartActivity : BaseActivity<StartContract.View, StartContract.Presenter, ActivityStartBinding>(
    layoutResource = R.layout.activity_start
), StartContract.View {

    override val downloadTrigger: Observable<Unit> get() = binding.bodyTextView.clicks().share() //todo
    override val presenter: StartContract.Presenter by inject()

    override fun setState(state: StartContract.State) {
        state
            .map(StartStateToBodyTextResourceMapper())
            .run(binding.bodyTextView::setText)
    }

    override fun navigateToComicGalleryScreen() {
        Toast.makeText(this, "nav", Toast.LENGTH_SHORT).show()
    }
}