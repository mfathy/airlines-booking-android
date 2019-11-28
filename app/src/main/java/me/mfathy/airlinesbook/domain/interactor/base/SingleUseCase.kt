package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

/**
 * SingleUseCase is is an abstract class which provide a Single observable to
 * emit required data or error.
 */
abstract class SingleUseCase<T, in Params> {

    abstract fun buildUseCaseObservable(params: Params): Single<T>

    fun execute(params: Params, observer: DisposableSingleObserver<T>): DisposableSingleObserver<T> {
        return this.buildUseCaseObservable(params)
                .subscribeWith(observer)

    }

    fun execute(params: Params): Single<T> {
        return this.buildUseCaseObservable(params)
    }

}