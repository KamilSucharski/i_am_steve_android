package com.iamsteve.android.view.list

import android.graphics.BitmapFactory
import com.iamsteve.android.R
import com.iamsteve.android.databinding.ItemComicPanelBinding
import com.iamsteve.android.util.adapter.Adapter
import java.io.File

class ComicPanelItem(
    private val file: File
) : Adapter.Item<ItemComicPanelBinding>(
    layoutResource = R.layout.item_comic_panel,
    viewTypeResource = R.id.adapter_item_comic_panel
) {

    override fun setData(binding: ItemComicPanelBinding) {
        binding.imageView.setImageBitmap(BitmapFactory.decodeFile(file.absolutePath))
    }
}