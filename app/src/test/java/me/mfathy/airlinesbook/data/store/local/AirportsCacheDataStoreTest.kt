package me.mfathy.airlinesbook.data.store.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.airlinesbook.data.mapper.cache.AccessTokenEntityCacheMapper
import me.mfathy.airlinesbook.data.mapper.cache.AirportEntityCacheMapper
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 */
@RunWith(RobolectricTestRunner::class)
class AirportsCacheDataStoreTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            AirportsDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    private val entityTokenMapper = AccessTokenEntityCacheMapper()
    private val entityAirportsMapper = AirportEntityCacheMapper()
    private val cacheStore = AirportsCacheDataStore(database, entityTokenMapper, entityAirportsMapper)


    @Test
    fun testGetAccessTokenReturnsData() {
        val token = AirportFactory.makeAccessTokenEntity()
        cacheStore.saveAccessToken(token).test()

        val testObserver = cacheStore.getAccessToken(token.clintId, "", "").test()
        testObserver.assertValue(token)
    }

    @Test
    fun testGetAirportsReturnsData() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirports(listOf(airportEntity)).test()

        val testObserver = cacheStore.getAirports("", 10, 1).test()
        testObserver.assertValue(listOf(airportEntity))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testGetFlightSchedulesThrowsException() {
        cacheStore.getFlightSchedules("", "", 10,1).test()
    }

    @Test
    fun testSaveAirportsCompletes() {
        val airportEntity = AirportFactory.makeAirportEntity()
        val testObserver = cacheStore.saveAirports(listOf(airportEntity)).test()

        testObserver.assertComplete()
    }

    @Test
    fun testClearAirportsCompletes() {
        val testObserver = cacheStore.clearAirports().test()
        testObserver.assertComplete()
    }

    @Test
    fun testSaveAccessTokenCompletes() {
        val accessTokenEntity = AirportFactory.makeAccessTokenEntity()
        val testObserver = cacheStore.saveAccessToken(accessTokenEntity).test()

        testObserver.assertComplete()
    }

    @Test
    fun testClearAccessTokenCompletes() {
        val testObserver = cacheStore.clearAccessToken().test()

        testObserver.assertComplete()
    }

    @Test
    fun testAreAirportsCachedReturnsTrue() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirports(listOf(airportEntity)).test()

        val testObserver = cacheStore.areAirportsCached().test()
        testObserver.assertValue(true)

    }

    @Test
    fun testIsAccessTokenCachedReturnsTrue() {
        val entity = AirportFactory.makeAccessTokenEntity()
        cacheStore.saveAccessToken(entity).test()

        val testObserver = cacheStore.isAccessTokenCached().test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsCacheExpiredReturnExpired() {
        cacheStore.setLastCacheTime(System.currentTimeMillis() - 10000).test()
        val testObserver = cacheStore.isCacheExpired().test()
        testObserver.assertValue(true)
    }

    @Test
    fun testSetLastCacheTimeCompletes() {
        val testObserver = cacheStore.setLastCacheTime(1000L).test()
        testObserver.assertComplete()
    }
}