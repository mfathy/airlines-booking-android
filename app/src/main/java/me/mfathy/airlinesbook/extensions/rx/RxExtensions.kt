package me.mfathy.airlinesbook.extensions.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */

fun <T : Any> Single<T>.subscribeAndObserve(): Single<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Flowable<T>.subscribeAndObserve(): Flowable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.subscribeAndObserve(): Observable<T> =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Flowable<T>.observeMainThread(): Flowable<T> =
        observeOn(AndroidSchedulers.mainThread())

fun Completable.subscribeAndObserve(): Completable =
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Single<T>.io(): Single<T> = subscribeOn(Schedulers.io())
fun <T : Any> Single<T>.computation(): Single<T> = subscribeOn(Schedulers.computation())
fun <T : Any> Single<T>.mainTheard(): Single<T> = observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Observable<T>.io(): Observable<T> = subscribeOn(Schedulers.io())
fun <T : Any> Observable<T>.computation(): Observable<T> = subscribeOn(Schedulers.computation())
fun <T : Any> Observable<T>.mainTheard(): Observable<T> = observeOn(AndroidSchedulers.mainThread())

fun <T : Any> Flowable<T>.io(): Flowable<T> = subscribeOn(Schedulers.io())
fun <T : Any> Flowable<T>.computation(): Flowable<T> = subscribeOn(Schedulers.computation())
fun <T : Any> Flowable<T>.mainTheard(): Flowable<T> = observeOn(AndroidSchedulers.mainThread())

fun Completable.io(): Completable = subscribeOn(Schedulers.io())
fun Completable.computation(): Completable = subscribeOn(Schedulers.computation())
fun Completable.mainThread(): Completable = observeOn(AndroidSchedulers.mainThread())