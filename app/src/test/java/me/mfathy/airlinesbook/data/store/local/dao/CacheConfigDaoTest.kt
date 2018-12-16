package me.mfathy.airlinesbook.data.store.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 */
@RunWith(RobolectricTestRunner::class)
class CacheConfigDaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            AirportsDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetCacheConfigReturnsData() {
        val cacheConfig = AirportFactory.makeCacheConfig()
        database.cacheConfigDao().insertCacheConfig(cacheConfig)

        val testObserver = database.cacheConfigDao().getCacheConfig().test()
        testObserver.assertValue(cacheConfig)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
    }

    @Test
    fun deleteCacheConfig() {
        val cacheConfig = AirportFactory.makeCacheConfig()
        database.cacheConfigDao().insertCacheConfig(cacheConfig)
        database.cacheConfigDao().deleteCacheConfig()

        val testObserver = database.cacheConfigDao().getCacheConfig().test()
        testObserver.assertNoValues()
    }
}