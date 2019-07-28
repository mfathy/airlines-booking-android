package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

/**
 * ObservableUseCase is is an abstract class which provide a Observable observable to
 * emit required data or error.
 */
abstract class ObservableUseCase<T, in Params> {

    abstract fun buildUseCaseObservable(params: Params): Observable<T>

    fun execute(params: Params, observer: DisposableObserver<T>): DisposableObserver<T> {
        return this.buildUseCaseObservable(params)
                .subscribeWith(observer)
    }

    fun execute(params: Params): Flowable<T> {
        return this.buildUseCaseObservable(params).toFlowable(BackpressureStrategy.DROP)
    }

}