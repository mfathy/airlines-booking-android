package me.mfathy.airlinesbook.ui.select

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.airports.GetAirports
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import me.mfathy.airlinesbook.ui.state.ResourceState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for SelectionViewModel
 */
@RunWith(JUnit4::class)
class SelectionViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val mockGetAirport: GetAirports = mock()
    var selectionViewModel = SelectionViewModel(mockGetAirport)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<AirportEntity>>>()

    @Test
    fun testFetchAirportsExecutesUseCase() {
        selectionViewModel.fetchAirports(DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt())

        verify(mockGetAirport, times(1)).execute(any(), any())
    }

    @Test
    fun testFetchAirportsReturnsSuccess() {
        val airports = listOf(AirportFactory.makeAirportEntity())

        selectionViewModel.fetchAirports(DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt())

        verify(mockGetAirport).execute(captor.capture(), any())
        captor.firstValue.onNext(airports)

        Assert.assertEquals(ResourceState.SUCCESS, selectionViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsData() {
        val airports = listOf(AirportFactory.makeAirportEntity())

        selectionViewModel.fetchAirports(DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt())

        verify(mockGetAirport).execute(captor.capture(), any())
        captor.firstValue.onNext(airports)


        Assert.assertEquals(airports, selectionViewModel.getAirportsLiveData().value?.data)
    }

    @Test
    fun testFetchAirportsReturnsError() {

        selectionViewModel.fetchAirports(DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt())

        verify(mockGetAirport).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        Assert.assertEquals(ResourceState.ERROR, selectionViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsMessageForError() {
        val errorMessage = DataFactory.randomString()
        selectionViewModel.fetchAirports(DataFactory.randomString(), DataFactory.randomInt(), DataFactory.randomInt())

        verify(mockGetAirport).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        Assert.assertEquals(errorMessage, selectionViewModel.getAirportsLiveData().value?.message)
    }
}