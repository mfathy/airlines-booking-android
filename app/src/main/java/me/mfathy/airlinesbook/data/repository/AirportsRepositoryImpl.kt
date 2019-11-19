package me.mfathy.airlinesbook.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import javax.inject.Inject

/**
 * AirportsRepository implementation
 */
class AirportsRepositoryImpl @Inject constructor(
        private val factory: AirportsDataStoreFactory
) : AirportsRepository {

    override fun getAirports(lang: String, limit: Int, offset: Int): Observable<List<AirportEntity>> {
        val cachedAirportCount = if (offset == 0) limit else (offset + 1) * limit
        return Observable.zip(
                //  Check if airports is cached.
                factory.getCacheDataStore().areAirportsCached(cachedAirportCount).toObservable(),
                //  Check if cache is expired.
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

    override fun getAirport(airportCode: String, lang: String, limit: Int, offset: Int): Single<AirportEntity> {
        return Single.zip(
                factory.getCacheDataStore().isAirportCached(airportCode),
                factory.getCacheDataStore().isCacheExpired(),
                BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { isCached, isExpired ->
                    Pair(isCached, isExpired)
                }).flatMap {
            factory.getDataStore(it.first, it.second)
                    .getAirport(airportCode, lang, limit, offset)
        }.flatMap {
            factory.getCacheDataStore()
                    .saveAirport(it)
                    .andThen(Single.just(it))

        }
    }

    override fun clearAirports(): Completable {
        return factory.getCacheDataStore()
                .clearAirports()
    }
}