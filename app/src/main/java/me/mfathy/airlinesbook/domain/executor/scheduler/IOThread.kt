package me.mfathy.airlinesbook.domain.executor.scheduler

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import javax.inject.Inject

/**
 * IOThread is a Scheduler provider, which provides IO scheduler.
 */
class IOThread @Inject constructor() : SubscribeThread {

    override val scheduler: Scheduler
        get() = Schedulers.io()
}