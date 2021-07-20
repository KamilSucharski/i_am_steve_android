package com.iamsteve.android.util.implementation

import com.iamsteve.domain.util.abstraction.RxSchedulers
import io.reactivex.Scheduler

class AndroidRxSchedulers(
    override val observeThread: Scheduler,
    override val subscribeThread: Scheduler
) : RxSchedulers