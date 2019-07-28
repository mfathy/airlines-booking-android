package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.ImmediateSchedulerRuleUnitTests
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.AirportFactory.makeGetScheduleFlightDetailsParams
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetScheduleFlightDetails
 */
@RunWith(MockitoJUnitRunner::class)
class GetScheduleFlightDetailsTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var mGetScheduleFlightDetails: GetScheduleFlightDetails

    @Mock
    lateinit var mockDataRepository: AirportsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetScheduleFlightDetails = GetScheduleFlightDetails(mockDataRepository)
    }

    @Test
    fun testGetScheduleFlightDetailsCompletes() {
        val entity = AirportFactory.makeAirportEntity()

        stubDataRepositoryGetScheduleFlightDetails(Flowable.just(listOf(entity)))

        val params = makeGetScheduleFlightDetailsParams()

        mGetScheduleFlightDetails.buildUseCaseObservable(params)
                .test()
                .assertComplete()
    }

    @Test
    fun testGetScheduleFlightDetailsReturnsValue() {
        val entity = AirportFactory.makeAirportEntity()

        stubDataRepositoryGetScheduleFlightDetails(Flowable.just(listOf(entity)))

        val params = makeGetScheduleFlightDetailsParams()

        mGetScheduleFlightDetails.buildUseCaseObservable(params)
                .test()
                .assertValue(listOf(entity))
    }

    @Test
    fun testGetScheduleFlightDetailsCallsRepository() {
        val entity = AirportFactory.makeAirportEntity()
        stubDataRepositoryGetScheduleFlightDetails(Flowable.just(listOf(entity)))

        val params = makeGetScheduleFlightDetailsParams()

        mGetScheduleFlightDetails.buildUseCaseObservable(params)
                .test()

        verify(mockDataRepository).getFlightScheduleDetails(me.mfathy.airlinesbook.any(), anyString(), anyInt(), anyInt())
    }

    private fun stubDataRepositoryGetScheduleFlightDetails(flowbale: Flowable<List<AirportEntity>>) {
        `when`(mockDataRepository.getFlightScheduleDetails(
                me.mfathy.airlinesbook.any(), anyString(), anyInt(), anyInt()
        )).thenReturn(flowbale)
    }
}
