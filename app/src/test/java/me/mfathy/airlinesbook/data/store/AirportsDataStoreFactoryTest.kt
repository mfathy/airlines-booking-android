package me.mfathy.airlinesbook.data.store

import com.nhaarman.mockito_kotlin.mock
import me.mfathy.airlinesbook.data.store.local.AirportsCacheDataStore
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import org.junit.Test

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsDataStoreFactory
 */
class AirportsDataStoreFactoryTest {

    private val mockCacheStore = mock<AirportsCacheDataStore>()
    private val mockRemoteStore = mock<AirportsRemoteDataStore>()
    private val factory = AirportsDataStoreFactory(
            mockCacheStore,
            mockRemoteStore
    )

    @Test
    fun getDataStoreReturnsRemoteSourceWhenCacheExpired() {
        assert(factory.getDataStore(false, true) is AirportsRemoteDataStore)
    }

    @Test
    fun getDataStoreReturnsCacheSourceWhenCacheNotExpired() {
        assert(factory.getDataStore(true, false) is AirportsCacheDataStore)
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