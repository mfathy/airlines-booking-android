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

@Entity(tableName = "access_token")
data class CachedAccessToken(
        @PrimaryKey
        @ColumnInfo(name = "client_id")
        val clientId: String = "",
        @ColumnInfo(name = "token")
        val accessToken: String = "",
        @ColumnInfo(name = "token_type")
        val tokenType: String = "",
        @ColumnInfo(name = "expires_in")
        val expiresIn: Int = 0)

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

@Entity(tableName = "cache_config")
data class CacheConfig(
        @PrimaryKey(autoGenerate = true)
        var id: Int = -1,
        var lastCacheTime: Long)
