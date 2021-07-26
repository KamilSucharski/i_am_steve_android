package com.iamsteve.android.view.comic.gallery

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityComicGalleryBinding
import com.iamsteve.android.util.pager.OnPageChangedSubject
import com.iamsteve.android.view.archive.ArchiveActivity
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.comic.gallery.adapter.ComicFragmentAdapter
import com.iamsteve.domain.exception.MissingArgumentException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.extension.cast
import com.iamsteve.domain.view.comic.gallery.ComicGalleryContract
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

    override fun setState(state: ComicGalleryContract.State) {
        if (binding.viewPager.adapter == null) {
            binding.viewPager.run {
                registerOnPageChangeCallback(OnPageChangedSubject(pageChangedTrigger))
                adapter = ComicFragmentAdapter(this@ComicGalleryActivity, comics)
                setCurrentItem(state.comics.lastIndex, false)
            }
        }

        binding.previousButton.isInvisible = !state.previousButtonVisible
        binding.nextButton.isInvisible = !state.nextButtonVisible
    }

    override fun setPosition(position: Int) {
        binding.viewPager.setCurrentItem(position, true)
    }

    override fun navigateToArchiveScreen(comics: List<Comic>) {
        ArchiveActivity.startForResult(
            context = this,
            comics = comics,
            resultLauncher = archiveActivityResultLauncher
        )
    }
}