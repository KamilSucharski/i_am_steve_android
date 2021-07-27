package com.iamsteve.android.view.list.mapper

import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.view.list.item.ComicArchiveItem
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.abstraction.Mapper
import com.iamsteve.domain.view.archive.ArchiveContract

class ArchiveItemMapper(
    private val onComicClicked: (Comic) -> Unit
) : Mapper<ArchiveContract.State, List<Adapter.Item<*>>> {

    override fun ArchiveContract.State.map() = comics.map {
        ComicArchiveItem(it, onComicClicked)
    }
}