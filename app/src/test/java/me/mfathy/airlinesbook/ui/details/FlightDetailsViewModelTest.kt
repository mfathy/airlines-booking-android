package me.mfathy.airlinesbook.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.schedules.GetScheduleFlightDetails
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import me.mfathy.airlinesbook.ui.state.ResourceState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit tests for FlightDetailsViewModel
 */
@RunWith(JUnit4::class)
class FlightDetailsViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private var mockGetScheduleFlightDetails: GetScheduleFlightDetails = mock()
    private var flightDetailsViewModel = FlightDetailsViewModel(mockGetScheduleFlightDetails)

    @Captor
    val captor = argumentCaptor<DisposableSubscriber<List<AirportEntity>>>()

    @Test
    fun testFetchAirportsExecutesUseCase() {
        flightDetailsViewModel.fetchAirports(listOf(), "en", 1, 1)

        verify(mockGetScheduleFlightDetails, times(1)).execute(any(), any())
    }

    @Test
    fun testFetchAirportsReturnsSuccess() {
        val airports = listOf(AirportFactory.makeAirportEntity())

        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(captor.capture(), any())
        captor.firstValue.onNext(airports)

        assertEquals(ResourceState.SUCCESS, flightDetailsViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsData() {
        val airports = listOf(AirportFactory.makeAirportEntity())

        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(captor.capture(), any())
        captor.firstValue.onNext(airports)

        assertEquals(airports, flightDetailsViewModel.getAirportsLiveData().value?.data)
    }

    @Test
    fun testFetchAirportsReturnsError() {
        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, flightDetailsViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        flightDetailsViewModel.fetchAirports(listOf(), "", 1, 1)

        verify(mockGetScheduleFlightDetails).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage, flightDetailsViewModel.getAirportsLiveData().value?.message)
    }
}