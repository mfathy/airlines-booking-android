package me.mfathy.airlinesbook.data.repository.auth

import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import me.mfathy.airlinesbook.data.store.local.AirportsCache
import me.mfathy.airlinesbook.data.store.remote.AirportsRemote
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsDataRepository
 */
@RunWith(MockitoJUnitRunner::class)
class AuthDataRepositoryTest {

    private val mockCacheStore = mock(AirportsCache::class.java)
    private val mockRemoteStore = mock(AirportsRemote::class.java)
    private val mockFactory = mock(AirportsDataStoreFactory::class.java)
    private val mockPreferenceHelper = mock(PreferenceHelper::class.java)
    private val repository = AuthDataRepository(mockFactory, mockPreferenceHelper)

    @Before
    fun setup() {
        stubFactoryGetCachedDataStore()
    }

    @Test
    fun testGetAccessTokenCompletes() {
        val entity = AccessTokenEntity(DataFactory.randomString())
        stubPreferenceHelper(entity)
        stubGetAccessTokenEntity(Single.just(entity))
        stubSetLastCacheTime(Completable.complete())
        stubFactoryGetRemoteDataStore()
        val testObserver = repository.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenReturnsData() {
        val entity = AccessTokenEntity(DataFactory.randomString())
        stubPreferenceHelper(entity)
        stubGetAccessTokenEntity(Single.just(entity))
        stubSetLastCacheTime(Completable.complete())
        stubFactoryGetRemoteDataStore()
        val testObserver = repository.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()).test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetCachedAccessTokenReturnsData() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubPreferenceHelper(entity)
        val testObserver = repository.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()).test()
        testObserver.assertValue(entity)
    }


    private fun stubPreferenceHelper(entity: AccessTokenEntity) {
        `when`(mockPreferenceHelper.getAccessToken()).thenReturn(entity)
    }

    private fun stubFactoryGetCachedDataStore() {
        `when`(mockFactory.getCacheDataStore()).thenReturn(mockCacheStore)
    }

    private fun stubFactoryGetRemoteDataStore() {
        `when`(mockFactory.getRemoteDataStore()).thenReturn(mockRemoteStore)
    }

    private fun stubSetLastCacheTime(completable: Completable) {
        `when`(mockCacheStore.setLastCacheTime(anyLong())).thenReturn(completable)
    }

    private fun stubGetAccessTokenEntity(observable: Single<AccessTokenEntity>) {
        `when`(mockRemoteStore.getAccessToken(anyString(), anyString(), anyString())).thenReturn(observable)
    }
}
