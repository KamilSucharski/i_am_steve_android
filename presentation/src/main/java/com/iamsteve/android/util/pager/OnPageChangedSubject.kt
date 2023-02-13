package com.iamsteve.android.util.pager

import androidx.viewpager2.widget.ViewPager2
import io.reactivex.rxjava3.subjects.Subject

class OnPageChangedSubject(private val subject: Subject<Int>) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        subject.onNext(position)
    }
}