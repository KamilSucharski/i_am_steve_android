package com.iamsteve.android.view.comic

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iamsteve.android.R
import com.iamsteve.android.databinding.FragmentComicBinding
import com.iamsteve.android.util.adapter.SimpleAdapter
import com.iamsteve.android.util.implementation.ToastErrorHandler
import com.iamsteve.android.view.base.BaseFragment
import com.iamsteve.android.view.comic.mapper.ComicItemMapper
import com.iamsteve.domain.exception.MissingArgumentException
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts
import com.iamsteve.domain.util.map
import com.iamsteve.domain.view.comic.ComicContract
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class ComicFragment : BaseFragment<ComicContract.View, ComicContract.Presenter, FragmentComicBinding>(
    layoutResource = R.layout.fragment_comic
), ComicContract.View {

    override val comic: Comic get() = arguments
        ?.getSerializable(Consts.EXTRA_COMIC) as? Comic
        ?: throw MissingArgumentException()
    override val presenter: ComicContract.Presenter by inject()
    override val errorHandler: ToastErrorHandler by inject { parametersOf({ activity }) }

    override fun setData(state: ComicContract.State) {
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