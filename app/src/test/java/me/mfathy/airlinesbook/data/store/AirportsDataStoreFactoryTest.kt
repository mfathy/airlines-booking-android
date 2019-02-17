package me.mfathy.airlinesbook.data.store

import me.mfathy.airlinesbook.data.store.local.AirportsCacheDataStore
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsDataStoreFactory
 */
class AirportsDataStoreFactoryTest {

    private val mockCacheStore = mock(AirportsCacheDataStore::class.java)
    private val mockRemoteStore = mock(AirportsRemoteDataStore::class.java)
    private val factory = AirportsDataStoreFactory(
            mockCacheStore,
            mockRemoteStore
    )

    @Test
    fun getDataStoreReturnsRemoteSourceWhenCacheExpired() {
        assert(factory.getDataStore(isCached = false, isCacheExpired = true) is AirportsRemoteDataStore)
    }

    @Test
    fun getDataStoreReturnsCacheSourceWhenCacheNotExpired() {
        assert(factory.getDataStore(isCached = true, isCacheExpired = false) is AirportsCacheDataStore)
    }

    @Test
    fun getCacheDataStoreRetrievesCacheSource() {
        assert(factory.getCacheDataStore() is AirportsCacheDataStore)
    }

    @Test
    fun getRemoteDataStoreRetrievesRemoteSource() {
        assert(factory.getRemoteDataStore() is AirportsRemoteDataStore)
    }
}
