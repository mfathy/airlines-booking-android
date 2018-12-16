package me.mfathy.airlinesbook.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import javax.inject.Inject

class AirportsRepositoryImpl @Inject constructor(private val factory: AirportsDataStoreFactory) : AirportsRepository {
    override fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {
        return Single.zip(
                factory.getCacheDataStore().isAccessTokenCached(),
                factory.getCacheDataStore().isCacheExpired(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                    Pair(isCached, isExpired)
                })
                .flatMap {
                    factory.getDataStore(it.first, it.second)
                            .getAccessToken(clientId, clientSecret, grantType)
                }
                .flatMap {
                    factory.getCacheDataStore().saveAccessToken(it)
                            .andThen(Single.just(it))
                }
    }

    override fun getAirports(lang: String, limit: Int, offset: Int): Observable<List<AirportEntity>> {
        return Observable.zip(
                factory.getCacheDataStore().areAirportsCached().toObservable(),
                factory.getCacheDataStore().isCacheExpired().toObservable(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                    Pair(isCached, isExpired)
                }).flatMap {
            factory.getDataStore(it.first, it.second)
                    .getAirports(lang, limit, offset)
                    .distinctUntilChanged()
                    .toObservable()
        }.flatMap { airports ->
            factory.getCacheDataStore().saveAirports(airports)
                    .andThen(Observable.just(airports))
        }

    }

    override fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>> {
        return factory.getRemoteDataStore()
                .getFlightSchedules(origin, destination, limit, offset)
    }

    override fun clearAirports(): Completable {
        return factory.getCacheDataStore()
                .clearAirports()
    }
}