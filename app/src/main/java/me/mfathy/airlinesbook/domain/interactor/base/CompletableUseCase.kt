package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread

/**
 * A CompletableUseCase is an abstract class which provide a Completable observable to
 * indicate completion or error.
 */
abstract class CompletableUseCase<in Params> constructor(
        private val subscriberThread: SubscribeThread,
        private val postExecutionThread: ExecutionThread) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
        val completable = this.buildUseCaseCompletable(params)
                .subscribeOn(subscriberThread.scheduler)
                .observeOn(postExecutionThread.scheduler)
        addDisposable(completable.subscribeWith(observer))
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}