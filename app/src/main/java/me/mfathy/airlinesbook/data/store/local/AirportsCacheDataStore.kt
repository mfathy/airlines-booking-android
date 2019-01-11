package me.mfathy.airlinesbook.data.store.local

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.mapper.cache.AirportEntityCacheMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * AirportsCacheDataStore is an implementation of AirportsDataStore to read/write
 * all local/cache related operations.
 */
open class AirportsCacheDataStore @Inject constructor(
        private val airportsDatabase: AirportsDatabase,
        private val airportMapper: AirportEntityCacheMapper
) : AirportsCache {

    override fun getAirports(lang: String, limit: Int, offset: Int): Flowable<List<AirportEntity>> {
        return airportsDatabase.cachedAirportsDao().getCachedAirports().map { airports ->
            airports.map { airportMapper.mapToEntity(it) }
        }
    }

    override fun getAirport(airportCode: String, lang: String, limit: Int, offset: Int): Single<AirportEntity> {
        return airportsDatabase.cachedAirportsDao().getCachedAirport(airportCode).map {
            airportMapper.mapToEntity(it)
        }
    }

    override fun getFlightSchedules(origin: String, destination: String, flightDate: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>> {
        throw UnsupportedOperationException("Unsupported operation to get cached flight schedules from cache")
    }

    override fun getFlightScheduleDetails(airportCodes: Array<String>): Flowable<List<AirportEntity>> {
        return airportsDatabase.cachedAirportsDao().getCachedAirportDetails(airportCodes).map { airports ->
            airports.map { airportMapper.mapToEntity(it) }
        }
    }

    override fun saveAirports(airportEntities: List<AirportEntity>): Completable {
        return Completable.defer {
            airportsDatabase.cachedAirportsDao().insertCachedAirports(
                    airportEntities.map { airportMapper.mapFromEntity(it) })
            Completable.complete()
        }
    }

    override fun saveAirport(airportEntity: AirportEntity): Completable {
        return Completable.defer {
            airportsDatabase.cachedAirportsDao().insertCachedAirport(airportMapper.mapFromEntity(airportEntity))
            Completable.complete()
        }
    }

    override fun clearAirports(): Completable {
        return Completable.defer {
            airportsDatabase.cachedAirportsDao().deleteCachedAirports()
            Completable.complete()
        }
    }

    override fun areAirportsCached(limit: Int): Single<Boolean> {
        return airportsDatabase.cachedAirportsDao().getCachedAirportsCount()
                .map { it >= limit }
    }

    override fun isAirportCached(airportCode: String): Single<Boolean> {
        return airportsDatabase.cachedAirportsDao().getCachedAirportCount(airportCode)
                .map { it > 0 }
    }

    override fun isCacheExpired(): Single<Boolean> {
        val currentTime = System.currentTimeMillis()
        return airportsDatabase.cacheConfigDao().getCacheConfig()
                .onErrorReturn {
                    it.printStackTrace()
                    CacheConfig(lastCacheTime = 0)
                }
                .map {
                    currentTime > it.lastCacheTime
                }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {

        return Completable.defer {
            val config = CacheConfig(1, lastCacheTime = System.currentTimeMillis().plus(lastCache))
            airportsDatabase.cacheConfigDao().insertCacheConfig(config)
                    .subscribe()
            Completable.complete()
        }
    }
}