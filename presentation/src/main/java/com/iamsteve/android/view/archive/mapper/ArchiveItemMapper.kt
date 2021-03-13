package com.iamsteve.android.view.archive.mapper

import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.view.archive.list.ComicArchiveItem
import com.iamsteve.android.view.list.ComicPanelItem
import com.iamsteve.android.view.list.ComicTitleItem
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.comic.ComicContract

class ArchiveItemMapper(
    private val onComicClicked: (Comic) -> Unit
) : Mapper<List<Comic>, List<Adapter.Item<*>>> {

    override fun List<Comic>.map() = map {
        ComicArchiveItem(it, onComicClicked)
    }
}