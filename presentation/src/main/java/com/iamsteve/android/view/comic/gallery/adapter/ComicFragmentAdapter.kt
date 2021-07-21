package com.iamsteve.android.view.comic.gallery.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iamsteve.android.view.comic.single.ComicFragment
import com.iamsteve.domain.model.Comic
import com.iamsteve.domain.util.Consts


class ComicFragmentAdapter(
    activity: FragmentActivity,
    private val comics: List<Comic>
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val args = Bundle().apply {
            putSerializable(Consts.EXTRA_COMIC, comics[position])
        }
        return ComicFragment().apply {
            arguments = args
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }
}