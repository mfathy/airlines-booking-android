package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber

/**
 * FlowableUseCase is is an abstract class which provide a Flowable observable to
 * emit required data or error.
 *
 * This observable support backpressure.
 */
abstract class FlowableUseCase<T, in Params> {

    abstract fun buildUseCaseObservable(params: Params): Flowable<T>

    fun execute(params: Params, observer: DisposableSubscriber<T>): DisposableSubscriber<T> {
        return this.buildUseCaseObservable(params)
                .subscribeWith(observer)
    }
}