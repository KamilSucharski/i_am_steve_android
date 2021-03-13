package com.iamsteve.android.view.comic.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iamsteve.android.util.Argument
import com.iamsteve.android.view.comic.ComicFragment
import com.iamsteve.domain.model.Comic


class ComicFragmentAdapter(
    activity: FragmentActivity,
    private val comics: List<Comic>
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val args = Bundle().apply {
            putSerializable(Argument.COMIC.name, comics[position])
        }
        return ComicFragment().apply {
            arguments = args
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }
}