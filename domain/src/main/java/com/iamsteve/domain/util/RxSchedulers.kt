package com.iamsteve.domain.util

import io.reactivex.Scheduler

interface RxSchedulers {
    val observeThread: Scheduler
    val subscribeThread: Scheduler
}