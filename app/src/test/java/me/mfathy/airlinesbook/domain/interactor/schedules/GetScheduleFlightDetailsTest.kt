package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
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
    private lateinit var mGetScheduleFlightDetails: GetScheduleFlightDetails

    @Mock
    lateinit var mockDataRepository: AirportsRepository
    @Mock
    lateinit var mockExecutionThread: ExecutionThread
    @Mock
    lateinit var mockSubscribeThread: SubscribeThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetScheduleFlightDetails = GetScheduleFlightDetails(mockDataRepository, mockSubscribeThread, mockExecutionThread)
    }

    @Test
    fun testGetScheduleFlightDetailsCompletes() {
        val entity = AirportFactory.makeAirportEntity()
        stubDataRepositoryGetScheduleFlightDetails(Flowable.just(listOf(entity)))

        val params = GetScheduleFlightDetails.Params.forGetScheduleFlightDetails(
                listOf(),
                DataFactory.randomString(),
                DataFactory.randomInt(),
                DataFactory.randomInt()
        )

        val testObserver = mGetScheduleFlightDetails.buildUseCaseObservable(params).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetScheduleFlightDetailsCallsRepository() {
        val entity = AirportFactory.makeAirportEntity()
        stubDataRepositoryGetScheduleFlightDetails(Flowable.just(listOf(entity)))

        val params = GetScheduleFlightDetails.Params.forGetScheduleFlightDetails(
                listOf(),
                DataFactory.randomString(),
                DataFactory.randomInt(),
                DataFactory.randomInt()
        )

        mGetScheduleFlightDetails.buildUseCaseObservable(params).test()
        verify(mockDataRepository).getFlightScheduleDetails(me.mfathy.airlinesbook.any(), anyString(), anyInt(), anyInt())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetScheduleFlightDetailsThrowsException() {
        mGetScheduleFlightDetails.buildUseCaseObservable(null).test()
    }

    private fun stubDataRepositoryGetScheduleFlightDetails(flowbale: Flowable<List<AirportEntity>>) {
        `when`(mockDataRepository.getFlightScheduleDetails(
                me.mfathy.airlinesbook.any(), anyString(), anyInt(), anyInt()
        )).thenReturn(flowbale)
    }
}
