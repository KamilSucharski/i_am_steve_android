package com.iamsteve.android.view.start.mapper

import com.iamsteve.domain.util.Mapper
import com.iamsteve.domain.view.start.StartContract

class StartStateToRetryButtonVisibilityMapper : Mapper<StartContract.State, Boolean> {

    override fun StartContract.State.map() = when (this) {
        StartContract.State.CONNECTION_ERROR -> true
        else -> false
    }
}