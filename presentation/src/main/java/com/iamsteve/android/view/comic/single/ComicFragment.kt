package com.iamsteve.android.view.comic.single

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamsteve.android.R
import com.iamsteve.android.databinding.FragmentComicBinding
import com.iamsteve.android.util.adapter.SimpleAdapter
import com.iamsteve.android.util.extension.serializable
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.view.base.BaseFragment
import com.iamsteve.android.view.list.mapper.ComicItemMapper
import com.iamsteve.domain.exception.MissingArgumentException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.abstraction.map
import com.iamsteve.domain.view.comic.single.ComicPresenter
import com.iamsteve.domain.view.comic.single.ComicView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ComicFragment : BaseFragment<ComicView, FragmentComicBinding>(
    layoutResource = R.layout.fragment_comic
), ComicView {

    override val comic: Comic
        get() = arguments
            ?.serializable(Consts.EXTRA_COMIC)
            ?: throw MissingArgumentException()
    override val presenter by inject<ComicPresenter>()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ activity }) }

    override fun setState(state: ComicView.State) {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            binding.recyclerView.context,
            RecyclerView.VERTICAL,
            false
        )
        binding.recyclerView.adapter = SimpleAdapter().apply {
            setData(state.map(ComicItemMapper()))
        }
    }
}