package com.iamsteve.android.view.comic

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityComicGalleryBinding
import com.iamsteve.android.util.pager.OnPageChangedSubject
import com.iamsteve.android.view.archive.ArchiveActivity
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.comic.adapter.ComicFragmentAdapter
import com.iamsteve.domain.exception.MissingArgumentException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.extension.cast
import com.iamsteve.domain.view.comic.ComicGalleryContract
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject

class ComicGalleryActivity : BaseActivity<ComicGalleryContract.View, ComicGalleryContract.Presenter, ActivityComicGalleryBinding>(
    layoutResource = R.layout.activity_comic_gallery
), ComicGalleryContract.View {

    override val comics: List<Comic>
        get() = intent
            .extras
            ?.getSerializable(Consts.EXTRA_COMICS)
            ?.cast()
            ?: throw MissingArgumentException()
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
    private val archiveActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        ArchiveActivity.parseResult(it)?.let(comicSelectedInArchiveTrigger::onNext)
    }

    companion object {
        fun start(context: Context, comics: List<Comic>) {
            Intent(context, ComicGalleryActivity::class.java)
                .putExtra(Consts.EXTRA_COMICS, ArrayList(comics))
                .let(context::startActivity)
        }
    }

    override fun displayComics(comics: List<Comic>) {
        binding.viewPager.run {
            registerOnPageChangeCallback(OnPageChangedSubject(pageChangedTrigger))
            adapter = ComicFragmentAdapter(this@ComicGalleryActivity, comics)
            setCurrentItem(comics.lastIndex, false)
            setButtonVisibility(previousButtonVisible = true, nextButtonVisible = false)
        }
    }

    override fun setPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    override fun setButtonVisibility(previousButtonVisible: Boolean, nextButtonVisible: Boolean) {
        binding.previousButton.visibility =
            if (previousButtonVisible) View.VISIBLE else View.INVISIBLE
        binding.nextButton.visibility = if (nextButtonVisible) View.VISIBLE else View.INVISIBLE
    }

    override fun navigateToArchiveScreen(comics: List<Comic>) {
        ArchiveActivity.startForResult(
            context = this,
            comics = comics,
            resultLauncher = archiveActivityResultLauncher
        )
    }
}