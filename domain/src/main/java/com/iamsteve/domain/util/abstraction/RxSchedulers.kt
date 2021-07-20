package com.iamsteve.domain.util.abstraction

import io.reactivex.Scheduler

interface RxSchedulers {
    val observeThread: Scheduler
    val subscribeThread: Scheduler
}