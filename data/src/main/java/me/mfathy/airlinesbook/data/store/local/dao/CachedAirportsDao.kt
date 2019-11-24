package me.mfathy.airlinesbook.data.store.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.config.AppConstants.DELETE_CACHED_AIRPORT
import me.mfathy.airlinesbook.data.config.AppConstants.QUERY_GET_CACHED_AIRPORT
import me.mfathy.airlinesbook.data.config.AppConstants.QUERY_GET_CACHED_AIRPORTS
import me.mfathy.airlinesbook.data.config.AppConstants.QUERY_GET_CACHED_AIRPORTS_COUNT
import me.mfathy.airlinesbook.data.config.AppConstants.QUERY_GET_CACHED_AIRPORTS_DETAILS
import me.mfathy.airlinesbook.data.config.AppConstants.QUERY_GET_CACHED_AIRPORT_COUNT
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * A data access object class to read/write airports to the database.
 */
@Dao
abstract class CachedAirportsDao {

    @Query(QUERY_GET_CACHED_AIRPORTS)
    @JvmSuppressWildcards
    abstract fun getCachedAirports(): Flowable<List<CachedAirport>>

    @Query(QUERY_GET_CACHED_AIRPORT)
    @JvmSuppressWildcards
    abstract fun getCachedAirport(airportCode: String): Single<CachedAirport>

    @Query(QUERY_GET_CACHED_AIRPORTS_DETAILS)
    @JvmSuppressWildcards
    abstract fun getCachedAirportDetails(airportCodes: Array<String>): Flowable<List<CachedAirport>>

    @Query(QUERY_GET_CACHED_AIRPORTS_COUNT)
    @JvmSuppressWildcards
    abstract fun getCachedAirportsCount(): Single<Int>

    @Query(QUERY_GET_CACHED_AIRPORT_COUNT)
    @JvmSuppressWildcards
    abstract fun getCachedAirportCount(airportCode: String): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertCachedAirports(cachedAirports: List<CachedAirport>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertCachedAirport(cachedAirport: CachedAirport)

    @Query(DELETE_CACHED_AIRPORT)
    abstract fun deleteCachedAirports()

}