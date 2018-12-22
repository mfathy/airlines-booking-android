package me.mfathy.airlinesbook.data.store.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * CachedModels: a kotlin file that contains all the cached layer models required by the CacheDataStore.
 */
@Entity(tableName = "airports")
data class CachedAirport(
        @ColumnInfo(name = "airport_name")
        val name: String = "",
        @PrimaryKey
        @ColumnInfo(name = "airport_code")
        val airportCode: String = "",
        @ColumnInfo(name = "latitude")
        val latitude: Double = 0.0,
        @ColumnInfo(name = "longitude")
        val longitude: Double = 0.0,
        @ColumnInfo(name = "city_code")
        val cityCode: String = "",
        @ColumnInfo(name = "country_code")
        val countryCode: String = "",
        @ColumnInfo(name = "location_type")
        val locationType: String = "")

@Entity(tableName = "config")
data class CacheConfig(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 1,
        @ColumnInfo(name = "last_cache_time")
        var lastCacheTime: Long)
