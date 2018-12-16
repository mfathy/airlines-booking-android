package me.mfathy.airlinesbook.data.store.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import me.mfathy.airlinesbook.data.mapper.remote.AccessTokenEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.AirportEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.ScheduleEntityNetworkMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.remote.model.*
import me.mfathy.airlinesbook.data.store.remote.service.RemoteService
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 * * Unit tests for AirportsRemoteDataStore
 */
@RunWith(JUnit4::class)
class AirportsRemoteDataStoreTest {

    private val mockService: RemoteService = mock<RemoteService>()
    private val mockAccessMapper = mock<AccessTokenEntityNetworkMapper>()
    private val mockAirportsMapper = mock<AirportEntityNetworkMapper>()
    private val mockScheduleMapper = mock<ScheduleEntityNetworkMapper>()
    private val remoteDataStore = AirportsRemoteDataStore(mockService,
            mockAccessMapper,
            mockAirportsMapper,
            mockScheduleMapper
    )

    @Test
    fun testGetAccessTokenCompletes() {
        stubRemoteServiceGetAccessToken(Single.just(AirportFactory.makeAccessToken()))
        stubAccessTokenEntityNetworkMapperMapFromModel(any(), AirportFactory.makeAccessTokenEntity())
        val testObserver = remoteDataStore.getAccessToken(
                "7szzzfvzgkeufcva7b9w8y3q",
                "uEdW35yNZd",
                "client_credentials").test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenCallsServer() {
        stubRemoteServiceGetAccessToken(Single.just(AirportFactory.makeAccessToken()))
        stubAccessTokenEntityNetworkMapperMapFromModel(any(), AirportFactory.makeAccessTokenEntity())
        remoteDataStore.getAccessToken(
                "7szzzfvzgkeufcva7b9w8y3q",
                "uEdW35yNZd",
                "client_credentials").test()

        verify(mockService).getAccessToken(any(), any(), any())
    }

    @Test
    fun testGetAccessTokenReturnsData() {
        val entity = AirportFactory.makeAccessTokenEntity()
        val accessToken = AirportFactory.makeAccessToken()
        stubRemoteServiceGetAccessToken(Single.just(accessToken))
        stubAccessTokenEntityNetworkMapperMapFromModel(any(), entity)
        val testObserver = remoteDataStore.getAccessToken(
                "7szzzfvzgkeufcva7b9w8y3q",
                "uEdW35yNZd",
                "client_credentials").test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetAirportsCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirports(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        val testObserver = remoteDataStore.getAirports("en", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAirportsCallsServer() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirports(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        remoteDataStore.getAirports("en", 1, 1).test()
        verify(mockService).getAirports(any(), any(), any())
    }

    @Test
    fun testGetAirportsReturnsData() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirports(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        val testObserver = remoteDataStore.getAirports("en", 1, 1).test()
        testObserver.assertValue(listOf(entity))
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        val testObserver = remoteDataStore.getFlightSchedules("CAI", "RUH", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFlightSchedulesCallServer() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        remoteDataStore.getFlightSchedules("CAI", "RUH", 1, 1).test()
        verify(mockService).getFlightSchedules(anyString(), anyString(), any(), any())
    }

    @Test
    fun testGetFlightSchedulesReturnsData() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        val testObserver = remoteDataStore.getFlightSchedules("CAI", "RUH", 1, 1).test()
        testObserver.assertValue(listOf(entity))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testSaveAirportsThrowsException() {
        remoteDataStore.saveAirports(listOf())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testClearAirportsThrowsException() {
        remoteDataStore.clearAirports()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testSaveAccessTokenThrowsException() {
        remoteDataStore.saveAccessToken(AccessTokenEntity())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testClearAccessTokenThrowsException() {
        remoteDataStore.clearAccessToken()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAreAirportsCachedThrowsException() {
        remoteDataStore.areAirportsCached()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testIsAccessTokenCachedThrowsException() {
        remoteDataStore.isAccessTokenCached()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testIsCacheExpiredThrowsException() {
        remoteDataStore.isCacheExpired()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setLastCacheTimeThrowsException() {
        remoteDataStore.setLastCacheTime(0L)
    }

    private fun stubRemoteServiceGetAccessToken(observable: Single<AccessToken>) {
        whenever(mockService.getAccessToken(anyString(), anyString(), anyString()))
                .thenReturn(observable)
    }

    private fun stubAccessTokenEntityNetworkMapperMapFromModel(model: AccessToken, entity: AccessTokenEntity) {
        whenever(mockAccessMapper.mapToEntity(model)).thenReturn(entity)
    }

    private fun stubRemoteServiceGetAirports(single: Single<AirportsResponse>) {
        whenever(mockService.getAirports(any(), any(), any())).thenReturn(single)
    }

    private fun stubAirportEntityNetworkMapperMapFromModel(model: Airport, entity: AirportEntity) {
        whenever(mockAirportsMapper.mapToEntity(model)).thenReturn(entity)
    }

    private fun stubRemoteServiceGetFlightSchedules(single: Single<FlightSchedulesResponse>) {
        whenever(mockService.getFlightSchedules(any(), any(), any(), any())).thenReturn(single)
    }

    private fun stubScheduleEntityNetworkMapperMapFromModel(model: Schedule, entity: ScheduleEntity) {
        whenever(mockScheduleMapper.mapToEntity(model)).thenReturn(entity)
    }
}