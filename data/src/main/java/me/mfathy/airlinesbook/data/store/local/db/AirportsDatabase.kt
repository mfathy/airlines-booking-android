package me.mfathy.airlinesbook.data.store.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.mfathy.airlinesbook.data.config.AppConstants.APP_DATABASE_NAME
import me.mfathy.airlinesbook.data.store.local.dao.CacheConfigDao
import me.mfathy.airlinesbook.data.store.local.dao.CachedAirportsDao
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport

/**
 * AirportsDatabase: the room database initializer.
 */
@Database(entities = [CachedAirport::class, CacheConfig::class], version = 1)
abstract class AirportsDatabase : RoomDatabase() {

    abstract fun cachedAirportsDao(): CachedAirportsDao

    abstract fun cacheConfigDao(): CacheConfigDao

    companion object {

        private var INSTANCE: AirportsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AirportsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AirportsDatabase::class.java, APP_DATABASE_NAME).build()
                    }
                    return INSTANCE as AirportsDatabase
                }
            }
            return INSTANCE as AirportsDatabase
        }
    }

}