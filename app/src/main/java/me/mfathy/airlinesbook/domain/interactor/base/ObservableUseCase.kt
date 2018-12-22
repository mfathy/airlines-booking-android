package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread

/**
 * ObservableUseCase is is an abstract class which provide a Observable observable to
 * emit required data or error.
 */
abstract class ObservableUseCase<T, in Params> constructor(
        private val subscriberThread: SubscribeThread,
        private val postExecutionThread: ExecutionThread) {

    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseObservable(params: Params? = null): Observable<T>

    open fun execute(singleObserver: DisposableObserver<T>, params: Params? = null) {
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(subscriberThread.scheduler)
                .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

}