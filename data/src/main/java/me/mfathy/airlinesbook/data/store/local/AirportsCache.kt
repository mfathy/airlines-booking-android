package me.mfathy.airlinesbook.data.store.local

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 */
interface AirportsCache: AirportsDataStore{

    /**
     * Saves a list of AirportEntity to the local data store.
     * @param airportEntities a list of AirportEntity
     * @return Completable observable indicates success of failure.
     */
    fun saveAirports(airportEntities: List<AirportEntity>): Completable

    /**
     * Saves an AirportEntity to the local data store.
     * @param airportEntity An AirportEntity to be saved.
     * @return Completable observable indicates success of failure.
     */
    fun saveAirport(airportEntity: AirportEntity): Completable

    /**
     * Clears all AirportEntity from the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun clearAirports(): Completable

    /**
     * Check that there are airports cached in the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun areAirportsCached(limit: Int): Single<Boolean>

    /**
     * Check that there are airport cached in the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun isAirportCached(airportCode: String): Single<Boolean>

    /**
     * Check that access token cached in the local data store is expired.
     * @return Completable observable indicates success of failure.
     */
    fun isCacheExpired(): Single<Boolean>

    /**
     * Sets the last time the cache saved.
     * @return Completable observable indicates success of failure.
     */
    fun setLastCacheTime(lastCache: Long): Completable

    /**
     * Returns a flowable which emits a list of flight schedules details.
     */
    fun getFlightScheduleDetails(airportCodes: Array<String>): Flowable<List<AirportEntity>>
}