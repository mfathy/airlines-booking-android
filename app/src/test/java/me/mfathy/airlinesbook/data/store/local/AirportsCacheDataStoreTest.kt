package me.mfathy.airlinesbook.data.store.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.airlinesbook.data.mapper.cache.AirportEntityCacheMapper
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsCacheDataStore
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
    private val entityAirportsMapper = AirportEntityCacheMapper()
    private val cacheStore = AirportsCacheDataStore(database, entityAirportsMapper)


    @Test
    fun testGetAirportsReturnsData() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirports(listOf(airportEntity)).test()

        val testObserver = cacheStore.getAirports("", 10, 1).test()
        testObserver.assertValue(listOf(airportEntity))
    }

    @Test
    fun testGetAirportReturnsData() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirport(airportEntity).test()

        val testObserver = cacheStore.getAirport(airportEntity.airportCode, "en", 10, 1).test()
        testObserver.assertValue(airportEntity)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testGetFlightSchedulesThrowsException() {
        cacheStore.getFlightSchedules("", "", "2019-01-01", 10, 1).test()
    }

    @Test
    fun testGetFlightScheduleDetailsReturnsData() {
        val entity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirport(entity).test()

        val testObserver = cacheStore.getFlightScheduleDetails(arrayOf(entity.airportCode)).test()
        testObserver.assertValue(listOf(entity))
    }

    @Test
    fun testSaveAirportsCompletes() {
        val airportEntity = AirportFactory.makeAirportEntity()
        val testObserver = cacheStore.saveAirports(listOf(airportEntity)).test()

        testObserver.assertComplete()
    }

    @Test
    fun testSaveAirportCompletes() {
        val airportEntity = AirportFactory.makeAirportEntity()
        val testObserver = cacheStore.saveAirport(airportEntity).test()

        testObserver.assertComplete()
    }

    @Test
    fun testClearAirportsCompletes() {
        val testObserver = cacheStore.clearAirports().test()
        testObserver.assertComplete()
    }

    @Test
    fun testAreAirportsCachedReturnsTrue() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirports(listOf(airportEntity)).test()

        val testObserver = cacheStore.areAirportsCached(1).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsAirportCachedReturnsTrue() {
        val airportEntity = AirportFactory.makeAirportEntity()
        cacheStore.saveAirport(airportEntity).test()

        val testObserver = cacheStore.isAirportCached(airportEntity.airportCode).test()
        testObserver.assertValue(true)
    }

    @Test
    fun testIsCacheExpiredReturnExpired() {
        cacheStore.setLastCacheTime(System.currentTimeMillis() + 10000).test()
        val testObserver = cacheStore.isCacheExpired().test()
        testObserver.assertValue(false)
    }

    @Test
    fun testSetLastCacheTimeCompletes() {
        val testObserver = cacheStore.setLastCacheTime(1000L).test()
        testObserver.assertComplete()
    }

}