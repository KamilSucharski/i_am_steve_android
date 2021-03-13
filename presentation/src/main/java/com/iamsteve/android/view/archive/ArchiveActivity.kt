package com.iamsteve.android.view.archive

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ActivityArchiveBinding
import com.iamsteve.android.util.adapter.SimpleAdapter
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.view.archive.mapper.ArchiveItemMapper
import com.iamsteve.android.view.base.BaseActivity
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.map
import com.iamsteve.domain.view.archive.ArchiveContract
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ArchiveActivity : BaseActivity<ArchiveContract.View, ArchiveContract.Presenter, ActivityArchiveBinding>(
    layoutResource = R.layout.activity_archive
), ArchiveContract.View {

    override val comicTrigger = PublishSubject.create<Comic>()
    override val presenter: ArchiveContract.Presenter by inject()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ this }) }

    companion object {
        fun startForResult(activity: Activity) {
            val intent = Intent(activity, ArchiveActivity::class.java)
            activity.startActivityForResult(
                intent,
                Consts.REQUEST_CODE_ARCHIVE_COMIC,
                ActivityOptions.makeSceneTransitionAnimation(activity).toBundle()
            )
        }

        fun parseResult(requestCode: Int, resultCode: Int, data: Intent?): Comic? {
            return if (requestCode == Consts.REQUEST_CODE_ARCHIVE_COMIC && resultCode == Activity.RESULT_OK) {
                data?.getSerializableExtra(Consts.EXTRA_COMIC) as? Comic
            } else {
                null
            }
        }
    }

    override fun setData(comics: List<Comic>) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            binding.recyclerView.context,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = SimpleAdapter().apply {
            setData(comics.map(ArchiveItemMapper(comicTrigger::onNext)))
        }
    }

    override fun navigateToComic(comic: Comic) {
        val intent = Intent()
        intent.putExtra(Consts.EXTRA_COMIC, comic)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}