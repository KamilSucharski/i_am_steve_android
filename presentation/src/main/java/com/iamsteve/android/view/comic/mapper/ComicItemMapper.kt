package com.iamsteve.android.view.comic.mapper

import com.iamsteve.android.util.adapter.Adapter
import com.iamsteve.android.view.list.ComicPanelItem
import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.comic.ComicContract

class ComicItemMapper : Mapper<ComicContract.Data, List<Adapter.Item<*>>> {

    override fun ComicContract.Data.map() = listOf(
        ComicPanelItem(file = comicPanels.panel1),
        ComicPanelItem(file = comicPanels.panel2),
        ComicPanelItem(file = comicPanels.panel3),
        ComicPanelItem(file = comicPanels.panel4)
    )
}