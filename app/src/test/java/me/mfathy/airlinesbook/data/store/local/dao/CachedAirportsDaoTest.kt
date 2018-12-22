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
 *
 * Unit test for CachedAirportsDao
 */
@RunWith(RobolectricTestRunner::class)
class CachedAirportsDaoTest {

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
    fun testGetCachedAirportsReturnsData() {

        val cachedAirport = AirportFactory.makeCachedAirport()
        database.cachedAirportsDao().insertCachedAirports(listOf(cachedAirport))

        val testObserver = database.cachedAirportsDao().getCachedAirports().test()
        testObserver.assertValue(listOf(cachedAirport))
    }

    @Test
    fun testGetCachedAirportReturnsData() {

        val cachedAirport = AirportFactory.makeCachedAirport()
        database.cachedAirportsDao().insertCachedAirports(listOf(cachedAirport))

        val testObserver = database.cachedAirportsDao().getCachedAirport(cachedAirport.airportCode).test()
        testObserver.assertValue(cachedAirport)
    }

    @Test
    fun testDeleteAirportsRClearsData() {

        val cachedAirport = AirportFactory.makeCachedAirport()
        database.cachedAirportsDao().insertCachedAirports(listOf(cachedAirport))
        database.cachedAirportsDao().deleteCachedAirports()

        val testObserver = database.cachedAirportsDao().getCachedAirports().test()
        testObserver.assertValue(emptyList())
    }


}