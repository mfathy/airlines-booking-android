package me.mfathy.airlinesbook.data.store

import me.mfathy.airlinesbook.data.store.local.AirportsCache
import me.mfathy.airlinesbook.data.store.local.AirportsCacheDataStore
import me.mfathy.airlinesbook.data.store.memory.AuthMemoryStore
import me.mfathy.airlinesbook.data.store.memory.MemoryCache
import me.mfathy.airlinesbook.data.store.memory.models.InMemoryToken
import me.mfathy.airlinesbook.data.store.remote.AirportsRemote
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * AirportsDataStoreFactory is a factory class responsible for creating and providing the suitable data store
 * object.
 */
open class AirportsDataStoreFactory @Inject constructor(
        private val memoryDataStore: AuthMemoryStore,
        private val cacheDataStore: AirportsCacheDataStore,
        private val remoteDataStore: AirportsRemoteDataStore) {

    open fun getDataStore(isCached: Boolean, isCacheExpired: Boolean): AirportsDataStore {
        return if (isCached && !isCacheExpired) {
            cacheDataStore
        } else {
            remoteDataStore
        }
    }

    open fun getCacheDataStore(): AirportsCache {
        return cacheDataStore
    }

    open fun getRemoteDataStore(): AirportsRemote {
        return remoteDataStore
    }

    open fun getMemoryDataStore(): MemoryCache{
        return memoryDataStore
    }
}