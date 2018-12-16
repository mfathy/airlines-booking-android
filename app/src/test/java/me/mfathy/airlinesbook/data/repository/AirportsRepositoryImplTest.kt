package me.mfathy.airlinesbook.data.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class AirportsRepositoryImplTest {

    private val mockStore = mock<AirportsDataStore>()
    private val mockRemoteStore = mock<AirportsRemoteDataStore>()
    private val mockFactory = mock<AirportsDataStoreFactory>()
    private val repository = AirportsRepositoryImpl(mockFactory)

    @Before
    fun setup() {
        stubFactoryGetDataStore()
        stubFactoryGetCachedDataStore()
    }

    @Test
    fun testGetAccessTokenCompletes() {
        stubGetAccessToken(Single.just(AirportFactory.makeAccessTokenEntity()))
        stubIsAccessTokenCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAccessToken(Completable.complete())

        val testObserver = repository.getAccessToken("67s77as7sd", "5a3sda2a2", "").test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenReturnsData() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubGetAccessToken(Single.just(entity))
        stubIsAccessTokenCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAccessToken(Completable.complete())

        val testObserver = repository.getAccessToken("67s77as7sd", "5a3sda2a2", "").test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetAirportsCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        stubGetAirports(Flowable.just(listOf(entity)))
        stubAreAirportsCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirports(Completable.complete())

        val testObservable = repository.getAirports("en", 1, 1).test()
        testObservable.assertComplete()
    }

    @Test
    fun testGetAirportsReturnsData() {
        val entity = AirportFactory.makeAirportEntity()
        stubGetAirports(Flowable.just(listOf(entity)))
        stubAreAirportsCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirports(Completable.complete())

        val testObservable = repository.getAirports("en", 1, 1).test()
        testObservable.assertValue(listOf(entity))
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        stubFactoryGetRemoteDataStore()
        val testObserver = repository.getFlightSchedules("CAI", "RUH", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearAirportsCompletes() {
        stubClearAirports(Completable.complete())
        val testObserver = repository.clearAirports().test()
        testObserver.assertComplete()
    }

    private fun stubClearAirports(complete: Completable?) {
        whenever(mockStore.clearAirports()).thenReturn(complete)
    }

    private fun stubFactoryGetDataStore() {
        whenever(mockFactory.getDataStore(any(), any())).thenReturn(mockStore)
    }

    private fun stubFactoryGetCachedDataStore() {
        whenever(mockFactory.getCacheDataStore()).thenReturn(mockStore)
    }

    private fun stubFactoryGetRemoteDataStore() {
        whenever(mockFactory.getRemoteDataStore()).thenReturn(mockRemoteStore)
    }

    private fun stubGetAccessToken(single: Single<AccessTokenEntity>) {
        whenever(mockStore.getAccessToken(any(), any(), any())).thenReturn(single)
    }

    private fun stubIsAccessTokenCached(single: Single<Boolean>) {
        whenever(mockStore.isAccessTokenCached()).thenReturn(single)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(mockStore.isCacheExpired()).thenReturn(single)
    }

    private fun stubSaveAccessToken(completable: Completable) {
        whenever(mockStore.saveAccessToken(any()))
                .thenReturn(completable)
    }

    private fun stubSaveAirports(completable: Completable) {
        whenever(mockStore.saveAirports(any())).thenReturn(completable)
    }

    private fun stubAreAirportsCached(single: Single<Boolean>?) {
        whenever(mockStore.areAirportsCached()).thenReturn(single)
    }

    private fun stubGetAirports(observable: Flowable<List<AirportEntity>>?) {
        whenever(mockStore.getAirports(any(), any(), any())).thenReturn(observable)
    }
}