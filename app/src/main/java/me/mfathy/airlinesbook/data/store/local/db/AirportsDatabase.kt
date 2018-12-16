package me.mfathy.airlinesbook.data.store.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.mfathy.airlinesbook.data.store.local.dao.CacheConfigDao
import me.mfathy.airlinesbook.data.store.local.dao.CachedAccessTokenDao
import me.mfathy.airlinesbook.data.store.local.dao.CachedAirportsDao
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport

@Database(entities = [CachedAccessToken::class, CachedAirport::class, CacheConfig::class], version = 1)
abstract class AirportsDatabase : RoomDatabase() {

    abstract fun cachedAirportsDao(): CachedAirportsDao

    abstract fun cachedAccessTokenDao(): CachedAccessTokenDao

    abstract fun cacheConfigDao(): CacheConfigDao

    companion object {

        private var INSTANCE: AirportsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AirportsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AirportsDatabase::class.java, "airports.db").build()
                    }
                    return INSTANCE as AirportsDatabase
                }
            }
            return INSTANCE as AirportsDatabase
        }
    }

}