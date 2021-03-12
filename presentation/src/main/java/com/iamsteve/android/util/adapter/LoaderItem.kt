package com.iamsteve.android.util.adapter

import com.iamsteve.android.R
import com.iamsteve.android.databinding.ItemLoaderBinding

class LoaderItem : Adapter.Item<ItemLoaderBinding>(
    layoutResource = R.layout.item_loader,
    viewTypeResource = R.id.adapter_item_loader
)