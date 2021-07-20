package com.iamsteve.android.view.comic.mapper

import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.view.list.ComicPanelItem
import com.iamsteve.android.view.list.ComicTitleItem
import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.comic.ComicContract

class ComicItemMapper : Mapper<ComicContract.State, List<Adapter.Item<*>>> {

    override fun ComicContract.State.map() = listOf(
        ComicPanelItem(bytes = comicPanels.panel1),
        ComicPanelItem(bytes = comicPanels.panel2),
        ComicPanelItem(bytes = comicPanels.panel3),
        ComicPanelItem(bytes = comicPanels.panel4),
        ComicTitleItem(comic = comic)
    )
}