package me.mfathy.airlinesbook.domain.interactor.base

import io.reactivex.Completable
import io.reactivex.CompletableObserver

/**
 * A CompletableUseCase is an abstract class which provide a Completable observable to
 * indicate completion or error.
 */
abstract class CompletableUseCase {

    abstract fun buildUseCaseCompletable(): Completable

    fun execute(observer: CompletableObserver): CompletableObserver {
        return this.buildUseCaseCompletable()
                .subscribeWith(observer)
    }
}