package me.mfathy.airlinesbook.data.store.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import dagger.Lazy
import io.reactivex.Single
import me.mfathy.airlinesbook.data.mapper.remote.AccessTokenEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.AirportEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.ScheduleEntityNetworkMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.remote.model.*
import me.mfathy.airlinesbook.data.store.remote.service.AuthServiceApi
import me.mfathy.airlinesbook.data.store.remote.service.RemoteServiceApi
import me.mfathy.airlinesbook.data.store.remote.utils.NetworkUtils
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for AirportsRemoteDataStore
 */
@RunWith(JUnit4::class)
class AirportsRemoteDataStoreTest {

    private val mockServiceApiLazy: Lazy<RemoteServiceApi> = mock()
    private val mockServiceApi: RemoteServiceApi = mock()
    private val mockNetworkUtils: NetworkUtils = mock()
    private val mockAuthServiceApi: AuthServiceApi = mock()
    private val mockAccessMapper = mock<AccessTokenEntityNetworkMapper>()
    private val mockAirportsMapper = mock<AirportEntityNetworkMapper>()
    private val mockScheduleMapper = mock<ScheduleEntityNetworkMapper>()
    private val remoteDataStore = AirportsRemoteDataStore(mockNetworkUtils,
            mockAuthServiceApi,
            mockServiceApiLazy,
            mockAccessMapper,
            mockAirportsMapper,
            mockScheduleMapper
    )

    @Before
    fun setUp() {
        stubRemoteService()
        stubNetworkUtils()
    }

    @Test
    fun testGetAccessTokenCompletes() {
        val token = AirportFactory.makeAccessToken()
        val entity = AirportFactory.makeAccessTokenEntity()
        stubAuthService(Single.just(token))
        stubAccessTokenEntityNetworkMapperMapFromModel(token, entity)
        val testObserver = remoteDataStore.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        ).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenReturnsData() {
        val token = AirportFactory.makeAccessToken()
        val entity = AirportFactory.makeAccessTokenEntity()
        stubAuthService(Single.just(token))
        stubAccessTokenEntityNetworkMapperMapFromModel(token, entity)
        val testObserver = remoteDataStore.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        ).test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetAccessTokenCallsServer() {
        val token = AirportFactory.makeAccessToken()
        val entity = AirportFactory.makeAccessTokenEntity()
        stubAuthService(Single.just(token))
        stubAccessTokenEntityNetworkMapperMapFromModel(token, entity)
        remoteDataStore.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        ).test()

        verify(mockAuthServiceApi).getAccessToken(any(), any(), any())
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
        verify(mockServiceApi).getAirports(any(), any(), any())
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
    fun testGetAirportCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirport(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        val testObserver = remoteDataStore.getAirport(DataFactory.randomString(), "en", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAirportCallsServer() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirport(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        remoteDataStore.getAirport(DataFactory.randomString(), "en", 1, 1).test()
        verify(mockServiceApi).getAirport(any(), any(), any(), any())
    }

    @Test
    fun testGetAirportReturnsData() {
        val entity = AirportFactory.makeAirportEntity()
        val airport = AirportFactory.makeAirport()
        val airportResponse = AirportsResponse(AirportResource(Airports(listOf(airport))))
        stubRemoteServiceGetAirport(Single.just(airportResponse))
        stubAirportEntityNetworkMapperMapFromModel(airport, entity)

        val testObserver = remoteDataStore.getAirport(DataFactory.randomString(), "en", 1, 1).test()
        testObserver.assertValue(entity)
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        val testObserver = remoteDataStore.getFlightSchedules("CAI", "RUH", "2019-01-01", 1, 1).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetFlightSchedulesCallServer() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        remoteDataStore.getFlightSchedules("CAI", "RUH", "2019-01-01", 1, 1).test()
        verify(mockServiceApi).getFlightSchedules(anyString(), anyString(), anyString(), any(), any())
    }

    @Test
    fun testGetFlightSchedulesReturnsData() {
        val entity = AirportFactory.makeScheduleEntity()
        val schedule = AirportFactory.makeSchedule()
        val schedulesResponse = FlightSchedulesResponse(ScheduleResource(listOf(schedule)))

        stubRemoteServiceGetFlightSchedules(Single.just(schedulesResponse))
        stubScheduleEntityNetworkMapperMapFromModel(schedule, entity)

        val testObserver = remoteDataStore.getFlightSchedules("CAI", "RUH", "2019-01-01", 1, 1).test()
        testObserver.assertValue(listOf(entity))
    }

    private fun stubRemoteService() {
        whenever(mockServiceApiLazy.get()).thenReturn(mockServiceApi)
    }

    private fun stubAuthService(single: Single<AccessToken>) {
        whenever(mockAuthServiceApi.getAccessToken(anyString(), anyString(), anyString())).thenReturn(single)
    }

    private fun stubAccessTokenEntityNetworkMapperMapFromModel(model: AccessToken, entity: AccessTokenEntity) {
        whenever(mockAccessMapper.mapToEntity(any())).thenReturn(entity)
    }

    private fun stubNetworkUtils() {
        whenever(mockNetworkUtils.hasConnection()).thenReturn(true)
    }

    private fun stubRemoteServiceGetAirports(single: Single<AirportsResponse>) {
        whenever(mockServiceApi.getAirports(any(), any(), any())).thenReturn(single)
    }

    private fun stubRemoteServiceGetAirport(single: Single<AirportsResponse>) {
        whenever(mockServiceApi.getAirport(any(), any(), any(), any())).thenReturn(single)
    }

    private fun stubAirportEntityNetworkMapperMapFromModel(model: Airport, entity: AirportEntity) {
        whenever(mockAirportsMapper.mapToEntity(model)).thenReturn(entity)
    }

    private fun stubRemoteServiceGetFlightSchedules(single: Single<FlightSchedulesResponse>) {
        whenever(mockServiceApi.getFlightSchedules(any(), any(), any(), any(), any())).thenReturn(single)
    }

    private fun stubScheduleEntityNetworkMapperMapFromModel(model: Schedule, entity: ScheduleEntity) {
        whenever(mockScheduleMapper.mapToEntity(model)).thenReturn(entity)
    }
}