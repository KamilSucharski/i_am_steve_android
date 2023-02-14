package com.iamsteve.domain.util.abstraction

import io.reactivex.rxjava3.core.Scheduler

data class ReactiveSchedulers(
    val observeThread: Scheduler,
    val subscribeThread: Scheduler
)