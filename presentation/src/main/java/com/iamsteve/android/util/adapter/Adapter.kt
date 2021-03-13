package com.iamsteve.android.util.adapter

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

interface Adapter {
    abstract class Item<DB : ViewDataBinding>(
        @LayoutRes val layoutResource: Int,
        @IdRes val viewTypeResource: Int
    ) {
        open fun setData(binding: DB) {}

        @Suppress("UNCHECKED_CAST")
        fun bindView(binding: ViewDataBinding) {
            (binding as? DB)?.run(::setData)
        }
    }

    class ViewHolderError : RuntimeException()

    val items: List<Item<out ViewDataBinding>>

    fun setData(items: List<Item<out ViewDataBinding>>)
}