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
        private val factory: AirportsDataStoreFactory,
        private val preferenceHelper: PreferenceHelper
) : AirportsRepository {

    override fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {
        //  Get cached access token.
        val accessToken = preferenceHelper.getAccessToken()
        return if (accessToken.accessToken.isBlank())
        //  Get access token from remote API.
            factory.getDataStore(false, true)
                    .getAccessToken(
                            clientId,
                            clientSecret,
                            grantType
                    ).flatMap {
                        //  Save cache expire trigger.
                        factory.getCacheDataStore().setLastCacheTime(it.expiresIn)
                                .andThen(Single.just(it))
                    }.doOnSuccess {
                        //  Save access token after getting it from Remote Api.
                        preferenceHelper.setAccessToken(it)
                    }
        else
            Single.just(accessToken)
    }

    override fun getAirports(lang: String, limit: Int, offset: Int): Observable<List<AirportEntity>> {
        return Observable.zip(
                //  Check if airports is cached.
                factory.getCacheDataStore().areAirportsCached(limit).toObservable(),
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

    override fun getFlightSchedules(origin: String, destination: String, flightDate: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>> {
        return factory.getDataStore(false, true)
                .getFlightSchedules(origin, destination, flightDate, limit, offset)
    }

    override fun getFlightScheduleDetails(airportCodes: Array<String>,
                                          lang: String,
                                          limit: Int,
                                          offset: Int): Flowable<List<AirportEntity>> {
        return Flowable.fromIterable(airportCodes.toMutableList())
                .flatMap { airportCode -> getAirport(airportCode, lang, limit, offset).toFlowable() }
                .toList()
                .toFlowable()
    }


    override fun clearAirports(): Completable {
        return factory.getCacheDataStore()
                .clearAirports()
    }
}