package me.mfathy.airlinesbook.data.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.BuildConfig
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import me.mfathy.airlinesbook.data.store.local.AirportsCache
import me.mfathy.airlinesbook.data.store.remote.AirportsRemote
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportsRepositoryImpl
 */
@RunWith(MockitoJUnitRunner::class)
class AirportsRepositoryImplTest {

    private val mockStore = mock<AirportsDataStore>()
    private val mockRemote = mock<AirportsRemote>()
    private val mockCache = mock<AirportsCache>()
    private val mockFactory = mock<AirportsDataStoreFactory>()
    private val mockPreferenceHelper = mock<PreferenceHelper>()
    private val repository = AirportsRepositoryImpl(mockFactory, mockPreferenceHelper)

    @Before
    fun setup() {
        stubFactoryGetDataStore()
        stubFactoryGetCachedDataStore()
        stubFactoryGetRemoteDataStore()
    }

    @Test
    fun testGetAccessTokenCompletes() {
        val entity = AccessTokenEntity(BuildConfig.CLIENT_ID)
        stubPreferenceHelper(entity)
        stubGetAccessTokenEntity(Single.just(entity))
        stubSetLastCacheTime(Completable.complete())
        val testObserver = repository.getAccessToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.GRANT_TYPE).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenReturnsData() {
        val entity = AccessTokenEntity(BuildConfig.CLIENT_ID)
        stubPreferenceHelper(entity)
        stubGetAccessTokenEntity(Single.just(entity))
        stubSetLastCacheTime(Completable.complete())
        val testObserver = repository.getAccessToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.GRANT_TYPE).test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetCachedAccessTokenReturnsData() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubPreferenceHelper(entity)
        val testObserver = repository.getAccessToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                BuildConfig.GRANT_TYPE).test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetAirportsCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        stubGetAirports(Flowable.just(listOf(entity)))
        stubAreAirportsCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirports(Completable.complete())
        stubSetLastCacheTime(Completable.complete())
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
        stubSetLastCacheTime(Completable.complete())
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
        stubSetLastCacheTime(Completable.complete())
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
        stubSetLastCacheTime(Completable.complete())
        val testObservable = repository.getAirport(entity.airportCode, "en", 1, 1).test()
        testObservable.assertValue(entity)
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        stubFactoryGetFlightSchedules()
        val testObserver = repository.getFlightSchedules("CAI", "RUH", "2019-01-01", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testClearAirportsCompletes() {
        stubClearAirports(Completable.complete())
        val testObserver = repository.clearAirports().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFlightScheduleDetailsCompletes() {
        val airportCodes = arrayOf("CAI")
        val airportEntity = AirportFactory.makeAirportEntity()
        stubGetAirport(Single.just(airportEntity))
        stubIsAirportCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirport(Completable.complete())
        stubSetLastCacheTime(Completable.complete())
        val testSubscriber = repository.getFlightScheduleDetails(airportCodes, "en", 1, 1).test()
        testSubscriber.assertComplete()
    }

    @Test
    fun testGetFlightScheduleDetailsReturnsData() {
        val airportCodes = arrayOf("CAI")
        val airportEntity = AirportFactory.makeAirportEntity()
        stubGetAirport(Single.just(airportEntity))
        stubIsAirportCached(Single.just(false))
        stubIsCacheExpired(Single.just(false))
        stubSaveAirport(Completable.complete())
        stubSetLastCacheTime(Completable.complete())
        val testSubscriber = repository.getFlightScheduleDetails(airportCodes, "en", 1, 1).test()
        testSubscriber.assertValue(listOf(airportEntity))
    }

    private fun stubPreferenceHelper(entity: AccessTokenEntity) {
        whenever(mockPreferenceHelper.getAccessToken()).thenReturn(entity)
    }

    private fun stubClearAirports(complete: Completable?) {
        whenever(mockCache.clearAirports()).thenReturn(complete)
    }

    private fun stubFactoryGetDataStore() {
        whenever(mockFactory.getDataStore(any(), any())).thenReturn(mockStore)
    }

    private fun stubFactoryGetCachedDataStore() {
        whenever(mockFactory.getCacheDataStore()).thenReturn(mockCache)
    }

    private fun stubFactoryGetRemoteDataStore() {
        whenever(mockFactory.getRemoteDataStore()).thenReturn(mockRemote)
    }

    private fun stubSetLastCacheTime(completable: Completable) {
        whenever(mockCache.setLastCacheTime(any())).thenReturn(completable)
    }

    private fun stubFactoryGetFlightSchedules() {
        whenever(mockStore.getFlightSchedules(
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(Flowable.just(listOf()))
    }


    private fun stubIsCacheExpired(single: Single<Boolean>) {
        whenever(mockCache.isCacheExpired()).thenReturn(single)
    }

    private fun stubSaveAirports(completable: Completable) {
        whenever(mockCache.saveAirports(any())).thenReturn(completable)
    }

    private fun stubSaveAirport(completable: Completable) {
        whenever(mockCache.saveAirport(any())).thenReturn(completable)
    }

    private fun stubAreAirportsCached(single: Single<Boolean>?) {
        whenever(mockCache.areAirportsCached(any())).thenReturn(single)
    }

    private fun stubIsAirportCached(single: Single<Boolean>?) {
        whenever(mockCache.isAirportCached(any())).thenReturn(single)
    }

    private fun stubGetAirports(observable: Flowable<List<AirportEntity>>?) {
        whenever(mockStore.getAirports(any(), any(), any())).thenReturn(observable)
    }

    private fun stubGetAirport(observable: Single<AirportEntity>?) {
        whenever(mockStore.getAirport(any(), any(), any(), any())).thenReturn(observable)
    }

    private fun stubGetAccessTokenEntity(observable: Single<AccessTokenEntity>) {
        whenever(mockRemote.getAccessToken(any(), any(), any())).thenReturn(observable)
    }
}