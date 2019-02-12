package me.mfathy.airlinesbook.data.store.remote

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
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*

/**
 * Created by Mohammed Fathy on 16/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for AirportsRemoteDataStore
 */
@RunWith(JUnit4::class)
class AirportsRemoteDataStoreTest {

    private val mockServiceApi = mock(RemoteServiceApi::class.java)
    private val mockServiceApiLazy = Lazy<RemoteServiceApi> {mockServiceApi}
    private val mockNetworkUtils = mock(NetworkUtils::class.java)
    private val mockAuthServiceApi= mock(AuthServiceApi::class.java)
    private val mockAccessMapper = mock(AccessTokenEntityNetworkMapper::class.java)
    private val mockAirportsMapper = mock(AirportEntityNetworkMapper::class.java)
    private val mockScheduleMapper = mock(ScheduleEntityNetworkMapper::class.java)
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
        stubAccessTokenEntityNetworkMapperMapFromModel(entity)
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
        stubAccessTokenEntityNetworkMapperMapFromModel(entity)
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
        stubAccessTokenEntityNetworkMapperMapFromModel(entity)
        remoteDataStore.getAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        ).test()

        verify(mockAuthServiceApi).getAccessToken(anyString(), anyString(), anyString())
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
        verify(mockServiceApi).getAirports(anyString(), anyInt(), anyInt())
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
        verify(mockServiceApi).getAirport(anyString(), anyString(), anyInt(), anyInt())
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
        verify(mockServiceApi).getFlightSchedules(anyString(), anyString(), anyString(), anyInt(), anyInt())
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

    @Test(expected = UnsupportedOperationException::class)
    fun testSaveAirportThrowsException() {
        remoteDataStore.saveAirport(AirportFactory.makeAirportEntity())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testSaveAirportsThrowsException() {
        remoteDataStore.saveAirports(listOf())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testGetFlightScheduleDetailsThrowsException() {
        remoteDataStore.getFlightScheduleDetails(arrayOf(""))
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testClearAirportsThrowsException() {
        remoteDataStore.clearAirports()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testAreAirportsCachedThrowsException() {
        remoteDataStore.areAirportsCached(1)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testIsAirportCachedThrowsException() {
        remoteDataStore.isAirportCached(DataFactory.randomString())
    }

    @Test(expected = UnsupportedOperationException::class)
    fun testIsCacheExpiredThrowsException() {
        remoteDataStore.isCacheExpired()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setLastCacheTimeThrowsException() {
        remoteDataStore.setLastCacheTime(0L)
    }


    private fun stubRemoteService() {
        `when`(mockServiceApiLazy.get()).thenReturn(mockServiceApi)
    }

    private fun stubAuthService(single: Single<AccessToken>) {
        `when`(mockAuthServiceApi.getAccessToken(anyString(), anyString(), anyString())).thenReturn(single)
    }

    private fun stubAccessTokenEntityNetworkMapperMapFromModel(entity: AccessTokenEntity) {
        `when`(mockAccessMapper.mapToEntity(any())).thenReturn(entity)
    }

    private fun stubNetworkUtils() {
        `when`(mockNetworkUtils.hasConnection()).thenReturn(true)
    }

    private fun stubRemoteServiceGetAirports(single: Single<AirportsResponse>) {
        `when`(mockServiceApi.getAirports(anyString(), anyInt(), anyInt())).thenReturn(single)
    }

    private fun stubRemoteServiceGetAirport(single: Single<AirportsResponse>) {
        `when`(mockServiceApi.getAirport(anyString(), anyString(), anyInt(), anyInt())).thenReturn(single)
    }

    private fun stubAirportEntityNetworkMapperMapFromModel(model: Airport, entity: AirportEntity) {
        `when`(mockAirportsMapper.mapToEntity(model)).thenReturn(entity)
    }

    private fun stubRemoteServiceGetFlightSchedules(single: Single<FlightSchedulesResponse>) {
        `when`(mockServiceApi.getFlightSchedules(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(single)
    }

    private fun stubScheduleEntityNetworkMapperMapFromModel(model: Schedule, entity: ScheduleEntity) {
        `when`(mockScheduleMapper.mapToEntity(model)).thenReturn(entity)
    }
}
