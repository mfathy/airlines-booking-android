package me.mfathy.airlinesbook.data.store

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 */
interface AirportsDataStore {

    /**
     * Returns an access token to be used in accessing the rest of remote API from the server.
     * @param clientId lufthansa application key.
     * @param clientSecret lufthansa application secret key.
     * @param grantType lufthansa grant access type
     * @return a Single observable which will emit an AccessTokenEntity or error.
     */
    fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity>

    /**
     * Returns a flowable which emits a list of airport entities.
     * @param lang the language the user would like to receive his response in.
     * @param limit the number of airports >> this should be from 1 to 100.
     * @param offset the paging number.
     * @return a flowable which emits a list of airport entities or error.
     */
    fun getAirports(lang: String, limit: Int, offset: Int): Flowable<List<AirportEntity>>

    /**
     * Returns a flowable which emits a list of flight schedule entities.
     * @param origin airport code that the user will travel from
     * @param destination airport code that the user will travel to
     * @param limit the number of flight schedules.
     * @return a flowable which emits a list of flight schedule entities or error.
     */
    fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>>

    /**
     * Saves a list of AirportEntity to the local data store.
     * @param airportEntities a list of AirportEntity
     * @return Completable observable indicates success of failure.
     */
    fun saveAirports(airportEntities: List<AirportEntity>): Completable

    /**
     * Clears all AirportEntity from the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun clearAirports(): Completable

    /**
     * Saves the access token to local data store.
     * @param accessTokenEntity remote access token
     * @return Completable observable indicates success of failure.
     */
    fun saveAccessToken(accessTokenEntity: AccessTokenEntity): Completable

    /**
     * Clears the access token from the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun clearAccessToken(): Completable

    /**
     * Check that there are airports cached in the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun areAirportsCached(): Single<Boolean>

    /**
     * Check that there are access token cached in the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun isAccessTokenCached(): Single<Boolean>

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
}