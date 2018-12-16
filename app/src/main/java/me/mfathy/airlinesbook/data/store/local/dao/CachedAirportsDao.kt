package me.mfathy.airlinesbook.data.store.local.dao

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
@Dao
abstract class CachedAirportsDao {

    @Query("SELECT * FROM AIRPORTS")
    @JvmSuppressWildcards
    abstract fun getCachedAirports():Flowable<List<CachedAirport>>

    @Query("SELECT * FROM AIRPORTS WHERE airport_code = :airportCode")
    @JvmSuppressWildcards
    abstract fun getCachedAirport(airportCode: String):Single<CachedAirport>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    abstract fun insertCachedAirports(cachedAirports: List<CachedAirport>)

    @Query("DELETE FROM AIRPORTS ")
    abstract fun deleteCachedAirports()

}