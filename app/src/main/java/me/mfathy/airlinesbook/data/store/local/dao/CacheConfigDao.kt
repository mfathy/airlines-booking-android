package me.mfathy.airlinesbook.data.store.local.dao

import androidx.room.*
import io.reactivex.Single
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig

@Dao
abstract class CacheConfigDao {

    @Query("SELECT * FROM CACHE_CONFIG")
    abstract fun getCacheConfig(): Single<CacheConfig>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCacheConfig(cacheConfig: CacheConfig)

    @Query("DELETE FROM CACHE_CONFIG")
    abstract fun deleteCacheConfig()

}