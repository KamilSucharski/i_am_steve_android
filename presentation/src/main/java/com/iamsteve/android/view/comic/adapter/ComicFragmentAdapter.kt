package com.iamsteve.android.view.comic.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.iamsteve.android.view.comic.ComicFragment
import com.iamsteve.domain.model.Comic

class ComicFragmentAdapter(
    activity: FragmentActivity,
    private val comics: List<Comic>
) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        return ComicFragment().apply {
//            setData(
//                imageResource = images[position],
//                headerTextResource = headers[position],
//                bodyTextResource = bodies[position]
//            )
            //todo
        }
    }

    override fun getItemCount(): Int {
        return comics.size
    }
}