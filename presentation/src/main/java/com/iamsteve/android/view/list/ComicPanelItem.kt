package com.iamsteve.android.view.list

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ItemComicPanelBinding
import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.util.extension.toBitmap

class ComicPanelItem(
    private val bytes: ByteArray
) : Adapter.Item<ItemComicPanelBinding>(
    layoutResource = R.layout.item_comic_panel,
    viewTypeResource = R.id.adapter_item_comic_panel
) {

    override fun setData(binding: ItemComicPanelBinding) {
        binding.imageView.setImageBitmap(bytes.toBitmap())
    }
}