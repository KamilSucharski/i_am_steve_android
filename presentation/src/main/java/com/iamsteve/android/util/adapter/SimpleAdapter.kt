package com.iamsteve.android.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class SimpleAdapter : RecyclerView.Adapter<SimpleViewHolder>(), Adapter {

    override var items: List<Adapter.Item<out ViewDataBinding>> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val item = items
            .firstOrNull { it.viewTypeResource == viewType }
            ?: throw Adapter.ViewHolderError()
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            item.layoutResource,
            parent,
            false
        )
        return SimpleViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int = items[position].viewTypeResource

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        val item = items[position]
        item.bindView(holder.binding)
    }

    override fun onViewRecycled(holder: SimpleViewHolder) {
        holder.binding.unbind()
    }

    override fun setData(items: List<Adapter.Item<out ViewDataBinding>>) {
        this.items = items
        notifyDataSetChanged()
    }
}