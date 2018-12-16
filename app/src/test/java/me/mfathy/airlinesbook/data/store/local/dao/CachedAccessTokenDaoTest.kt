package me.mfathy.airlinesbook.data.store.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.After
import org.junit.Test

import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 */
@RunWith(RobolectricTestRunner::class)
class CachedAccessTokenDaoTest {

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
    fun testGetCachedAccessTokenReturnsData() {
        val cachedToken = AirportFactory.makeCachedAccessToken()
        database.cachedAccessTokenDao().insertAccessToken(cachedToken).test()

        val testObserver = database.cachedAccessTokenDao().getCachedAccessToken(cachedToken.clientId).test()
        testObserver.assertValue(cachedToken)
    }

    @Test
    fun testGetAllCachedAccessTokenReturnsData() {
        val cachedToken01 = AirportFactory.makeCachedAccessToken()
        val cachedToken02 = AirportFactory.makeCachedAccessToken()
        listOf(cachedToken01, cachedToken02).forEach {
            database.cachedAccessTokenDao().insertAccessToken(it).test()
        }

        val testObserver = database.cachedAccessTokenDao().getCachedAccessToken(cachedToken01.clientId).test()
        testObserver.assertValue(cachedToken01)
    }

    @Test
    fun deleteAccessTokens() {
        val cachedAccess = AirportFactory.makeCachedAccessToken()
        database.cachedAccessTokenDao().insertAccessToken(cachedAccess).test()
        database.cachedAccessTokenDao().deleteAccessTokens()

        val testObserver = database.cachedAccessTokenDao().getCachedAccessToken(cachedAccess.clientId).test()
        testObserver.assertNoValues()
    }
}