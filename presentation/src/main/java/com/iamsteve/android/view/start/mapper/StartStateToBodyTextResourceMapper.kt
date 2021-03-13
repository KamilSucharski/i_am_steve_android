package com.iamsteve.android.view.start.mapper

import com.iamsteve.android.R
import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.start.StartContract

class StartStateToBodyTextResourceMapper : Mapper<StartContract.State, Int> {

    override fun StartContract.State.map() = when (this) {
        StartContract.State.PRELOADING_COMICS -> R.string.start_body_preloading_comics
        StartContract.State.DOWNLOADING_COMIC_LIST -> R.string.start_body_downloading_comic_list
        StartContract.State.DOWNLOADING_COMIC_PANELS -> R.string.start_body_downloading_comic_panels
        StartContract.State.ERROR -> R.string.start_body_error
    }
}