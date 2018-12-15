package me.mfathy.airlinesbook.domain.executor

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import javax.inject.Inject

/**
 * IOThread is a Scheduler provider, which provides IO scheduler.
 */
class IOThread @Inject constructor(): ExecutionThread {

    override val scheduler: Scheduler
        get() = Schedulers.io()
}