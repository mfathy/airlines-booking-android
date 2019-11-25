package me.mfathy.airlinesbook.data.repository.airports

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import me.mfathy.airlinesbook.data.store.local.AirportsCache
import me.mfathy.airlinesbook.factory.AirportFactory
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
class AirportsDataRepositoryTest {

    private val mockStore = mock(AirportsDataStore::class.java)
    private val mockCacheStore = mock(AirportsCache::class.java)
    private val mockFactory = mock(AirportsDataStoreFactory::class.java)
    private val repository = AirportsDataRepository(mockFactory)

    @Before
    fun setup() {
        stubFactoryGetDataStore()
        stubFactoryGetCachedDataStore()
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
    fun testGetAirportCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        stubGetAirport(Single.just(entity))
        stubIsAirportCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirport(Completable.complete())
        val testObservable = repository.getAirport(entity.airportCode, "en", 1, 1).test()
        testObservable.assertComplete()
    }

    @Test
    fun testGetAirportReturnsData() {
        val entity = AirportFactory.makeAirportEntity()
        stubGetAirport(Single.just(entity))
        stubIsAirportCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirport(Completable.complete())
        val testObservable = repository.getAirport(entity.airportCode, "en", 1, 1).test()
        testObservable.assertValue(entity)
    }

    @Test
    fun testClearAirportsCompletes() {
        stubClearAirports(Completable.complete())
        val testObserver = repository.clearAirports().test()
        testObserver.assertComplete()
    }

    private fun stubClearAirports(complete: Completable?) {
        `when`(mockCacheStore.clearAirports()).thenReturn(complete)
    }

    private fun stubFactoryGetDataStore() {
        `when`(mockFactory.getDataStore(anyBoolean(), anyBoolean())).thenReturn(mockStore)
    }

    private fun stubFactoryGetCachedDataStore() {
        `when`(mockFactory.getCacheDataStore()).thenReturn(mockCacheStore)
    }

    private fun stubIsCacheExpired(single: Single<Boolean>) {
        `when`(mockCacheStore.isCacheExpired()).thenReturn(single)
    }

    private fun stubSaveAirports(completable: Completable) {
        `when`(mockCacheStore.saveAirports(anyList())).thenReturn(completable)
    }

    private fun stubSaveAirport(completable: Completable) {
        `when`(mockCacheStore.saveAirport(me.mfathy.test.tools.any())).thenReturn(completable)
    }

    private fun stubAreAirportsCached(single: Single<Boolean>?) {
        `when`(mockCacheStore.areAirportsCached(anyInt())).thenReturn(single)
    }

    private fun stubIsAirportCached(single: Single<Boolean>?) {
        `when`(mockCacheStore.isAirportCached(anyString())).thenReturn(single)
    }

    private fun stubGetAirports(observable: Flowable<List<AirportEntity>>?) {
        `when`(mockStore.getAirports(anyString(), anyInt(), anyInt())).thenReturn(observable)
    }

    private fun stubGetAirport(observable: Single<AirportEntity>?) {
        `when`(mockStore.getAirport(anyString(), anyString(), anyInt(), anyInt())).thenReturn(observable)
    }

}
