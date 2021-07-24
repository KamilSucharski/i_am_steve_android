package com.iamsteve.android.view.archive.mapper

import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.view.list.ComicArchiveItem
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.abstraction.Mapper

class ArchiveItemMapper(
    private val onComicClicked: (Comic) -> Unit
) : Mapper<List<Comic>, List<Adapter.Item<*>>> {

    override fun List<Comic>.map() = map {
        ComicArchiveItem(it, onComicClicked)
    }
}