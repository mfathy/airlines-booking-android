package me.mfathy.airlinesbook.ui.select

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.Flowable
import me.mfathy.airlinesbook.ImmediateSchedulerRuleUnitTests
import me.mfathy.airlinesbook.any
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.airports.GetAirports
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.ui.state.ResourceState
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*

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

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private val mockGetAirport = mock(GetAirports::class.java)
    private var selectionViewModel = SelectionViewModel(mockGetAirport)


    @Test
    fun testFetchAirportsExecutesUseCase() {

        val airports = listOf(AirportFactory.makeAirportEntity())

        stubGetAirportsUseCase(Flowable.just(airports))

        selectionViewModel.startPagination()
        selectionViewModel.getPaginator().onNext(Pair(0, "en"))

        verify(mockGetAirport, times(1)).execute(any())
    }

    @Test
    fun testFetchAirportsReturnsSuccess() {

        val airports = listOf(AirportFactory.makeAirportEntity())

        stubGetAirportsUseCase(Flowable.just(airports))


        selectionViewModel.startPagination()
        selectionViewModel.getPaginator().onNext(Pair(0, "en"))

        verify(mockGetAirport).execute(any())

        Assert.assertEquals(ResourceState.SUCCESS, selectionViewModel.getAirportsLiveData().value?.status)
    }

    @Test
    fun testFetchAirportsReturnsData() {

        val airports = listOf(AirportFactory.makeAirportEntity())

        stubGetAirportsUseCase(Flowable.just(airports))

        selectionViewModel.startPagination()
        selectionViewModel.getPaginator().onNext(Pair(0, "en"))

        verify(mockGetAirport).execute(any())


        Assert.assertEquals(airports, selectionViewModel.getAirportsLiveData().value?.data)
    }

    private fun stubGetAirportsUseCase(param: Flowable<List<AirportEntity>>) {
        `when`(mockGetAirport.execute(any())).thenReturn(param)
    }


}
