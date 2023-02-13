package com.iamsteve.android.view.archive

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityArchiveBinding
import com.iamsteve.android.util.adapter.SimpleAdapter
import com.iamsteve.android.util.extension.serializable
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.android.view.list.mapper.ArchiveItemMapper
import com.iamsteve.domain.exception.MissingArgumentException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.map
import com.iamsteve.domain.view.archive.ArchivePresenter
import com.iamsteve.domain.view.archive.ArchiveView
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject

class ArchiveActivity : BaseActivity<ArchiveView, ActivityArchiveBinding>(
    layoutResource = R.layout.activity_archive
), ArchiveView {

    override val comics: List<Comic>
        get() = intent
            .extras
            ?.serializable(Consts.EXTRA_COMICS)
            ?: throw MissingArgumentException()
    override val comicTrigger: PublishSubject<Comic> = PublishSubject.create()
    override val presenter by inject<ArchivePresenter>()

    companion object {
        fun startForResult(
            context: Context,
            comics: List<Comic>,
            resultLauncher: ActivityResultLauncher<Intent>
        ) {
            Intent(context, ArchiveActivity::class.java)
                .putExtra(Consts.EXTRA_COMICS, ArrayList(comics))
                .let(resultLauncher::launch)
        }

        fun parseResult(activityResult: ActivityResult): Comic? {
            return activityResult
                .takeIf { it.resultCode == Activity.RESULT_OK }
                ?.data
                ?.serializable(Consts.EXTRA_COMIC)
        }
    }

    override fun setState(state: ArchiveView.State) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            binding.recyclerView.context,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = SimpleAdapter().apply {
            setData(state.map(ArchiveItemMapper(comicTrigger::onNext)))
        }
    }

    override fun navigateToComic(comic: Comic) {
        val intent = Intent().putExtra(Consts.EXTRA_COMIC, comic)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}