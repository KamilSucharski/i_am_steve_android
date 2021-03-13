package com.iamsteve.android.view.comic

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityComicGalleryBinding
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.util.pager.OnPageChangedSubject
import com.iamsteve.android.view.archive.ArchiveActivity
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.comic.adapter.ComicFragmentAdapter
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.view.comic.ComicGalleryContract
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ComicGalleryActivity : BaseActivity<ComicGalleryContract.View, ComicGalleryContract.Presenter, ActivityComicGalleryBinding>(
    layoutResource = R.layout.activity_comic_gallery
), ComicGalleryContract.View {

    override val pageChangedTrigger = PublishSubject.create<Int>()
    override val comicSelectedInArchiveTrigger = PublishSubject.create<Comic>()
    override val previousButtonTrigger: Observable<Unit>
        get() = binding.previousButton.clicks().share()
    override val archiveButtonTrigger: Observable<Unit>
        get() = binding.archiveButton.clicks().share()
    override val nextButtonTrigger: Observable<Unit>
        get() = binding.nextButton.clicks().share()
    override val currentPosition: Int
        get() = binding.viewPager.currentItem
    override val presenter: ComicGalleryContract.Presenter by inject()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ this }) }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ComicGalleryActivity::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

    override fun displayComics(comics: List<Comic>) {
        binding.viewPager.run {
            registerOnPageChangeCallback(OnPageChangedSubject(pageChangedTrigger))
            adapter = ComicFragmentAdapter(this@ComicGalleryActivity, comics)
            binding.viewPager.setCurrentItem(comics.lastIndex, false)
            setButtonVisibility(previousButtonVisible = true, nextButtonVisible = false)
        }
    }

    override fun setPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    override fun setButtonVisibility(previousButtonVisible: Boolean, nextButtonVisible: Boolean) {
        binding.previousButton.visibility = if (previousButtonVisible) View.VISIBLE else View.INVISIBLE
        binding.nextButton.visibility = if (nextButtonVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun navigateToArchiveScreen() {
        ArchiveActivity.startForResult(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ArchiveActivity
            .parseResult(requestCode, resultCode, data)
            ?.let(comicSelectedInArchiveTrigger::onNext)
    }
}