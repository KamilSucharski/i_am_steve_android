package com.iamsteve.android.view.archive.list

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ItemComicArchiveBinding
import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.domain.model.Comic

class ComicArchiveItem(
    private val comic: Comic,
    private val onComicClicked: (Comic) -> Unit
) : Adapter.Item<ItemComicArchiveBinding>(
    layoutResource = R.layout.item_comic_archive,
    viewTypeResource = R.id.adapter_item_comic_archive
) {

    override fun setData(binding: ItemComicArchiveBinding) {
        binding.textView.text = binding.root.context.getString(
            R.string.comic_archive_format,
            comic.number,
            comic.title,
            comic.date
        )
        binding.root.setOnClickListener { onComicClicked(comic) }
    }
}