package me.mfathy.airlinesbook.data.store.local

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.mfathy.airlinesbook.data.mapper.cache.AccessTokenEntityCacheMapper
import me.mfathy.airlinesbook.data.mapper.cache.AirportEntityCacheMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
open class AirportsCacheDataStore @Inject constructor(
        private val airportsDatabase: AirportsDatabase,
        private val accessTokenMapper: AccessTokenEntityCacheMapper,
        private val airportMapper: AirportEntityCacheMapper
) : AirportsDataStore {

    override fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {
        return airportsDatabase.cachedAccessTokenDao().getCachedAccessToken(clientId).map {
            accessTokenMapper.mapToEntity(it)
        }
    }

    override fun getAirports(lang: String, limit: Int, offset: Int): Flowable<List<AirportEntity>> {
        return airportsDatabase.cachedAirportsDao().getCachedAirports().map { airports ->
            airports.map { airportMapper.mapToEntity(it) }
        }
    }

    override fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>> {
        throw UnsupportedOperationException("Unsupported operation to get cached flight schedules from cache")
    }

    override fun saveAirports(airportEntities: List<AirportEntity>): Completable {
        return Completable.defer{
            airportsDatabase.cachedAirportsDao().insertCachedAirports(
                    airportEntities.map { airportMapper.mapFromEntity(it) })
            Completable.complete()
        }
    }

    override fun clearAirports(): Completable {
        return Completable.defer {
            airportsDatabase.cachedAirportsDao().deleteCachedAirports()
            Completable.complete()
        }
    }

    override fun saveAccessToken(accessTokenEntity: AccessTokenEntity): Completable {
        return Completable.defer {
            airportsDatabase.cachedAccessTokenDao().insertAccessToken(
                    accessTokenMapper.mapFromEntity(accessTokenEntity))
            Completable.complete()
        }
    }

    override fun clearAccessToken(): Completable {
        return Completable.defer {
            airportsDatabase.cachedAccessTokenDao().deleteAccessTokens()
            Completable.complete()
        }
    }

    override fun areAirportsCached(): Single<Boolean> {
        return airportsDatabase.cachedAirportsDao().getCachedAirports().isEmpty
                .map {
                    !it
                }
    }

    override fun isAccessTokenCached(): Single<Boolean> {
        return airportsDatabase.cachedAccessTokenDao().getAllCachedAccessToken().isEmpty
                .map {
                    !it
                }
    }

    override fun isCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        return airportsDatabase.cacheConfigDao().getCacheConfig()
                .onErrorReturn { CacheConfig(lastCacheTime = 0) }
                .map {
                    currentTime > it.lastCacheTime
                }


    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            airportsDatabase.cacheConfigDao().insertCacheConfig(CacheConfig(lastCacheTime = lastCache))
            Completable.complete()
        }
    }
}