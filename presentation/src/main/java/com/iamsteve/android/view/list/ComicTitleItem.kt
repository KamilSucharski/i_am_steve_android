package com.iamsteve.android.view.list

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ItemComicTitleBinding
import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.domain.model.Comic

class ComicTitleItem(
    private val comic: Comic
) : Adapter.Item<ItemComicTitleBinding>(
    layoutResource = R.layout.item_comic_title,
    viewTypeResource = R.id.adapter_item_comic_title
) {

    override fun setData(binding: ItemComicTitleBinding) {
        binding.textView.text = binding.root.context.getString(
            R.string.comic_title_format,
            comic.number,
            comic.title
        )
    }
}