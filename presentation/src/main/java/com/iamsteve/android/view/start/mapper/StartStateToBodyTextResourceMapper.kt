package com.iamsteve.android.view.start.mapper

import com.iamsteve.android.R
import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.start.StartContract

class StartStateToBodyTextResourceMapper : Mapper<StartContract.State, Int> {

    override fun StartContract.State.map() = when (this) {
        StartContract.State.DOWNLOADING_COMICS_METADATA -> R.string.start_body_downloading_comics_metadata
        StartContract.State.DOWNLOADING_COMICS -> R.string.start_body_downloading_comics
        StartContract.State.CONNECTION_ERROR -> R.string.start_body_connection_error
    }
}