package me.mfathy.airlinesbook.domain.interactor.schedules

import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetFlightSchedules use case.
 */
@RunWith(MockitoJUnitRunner::class)
class GetFlightSchedulesTest {
    private lateinit var mGetFlightSchedules: GetFlightSchedules

    @Mock
    lateinit var mockDataRepository: AirportsRepository
    @Mock
    lateinit var mockExecutionThread: ExecutionThread
    @Mock
    lateinit var mockSubscribeThread: SubscribeThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetFlightSchedules = GetFlightSchedules(mockDataRepository, mockSubscribeThread, mockExecutionThread)
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        stubDataRepositoryGetFlightSchedules(Flowable.just(listOf(
                AirportFactory.makeScheduleEntity(),
                AirportFactory.makeScheduleEntity()
        )))

        val params = GetFlightSchedules.Params.forGetFlightSchedules("CAI", "RUH", "2019-01-01", 10, 1)
        val testObserver = mGetFlightSchedules.buildUseCaseObservable(params).test()
        testObserver.assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetFlightSchedulesThrowsException() {
        mGetFlightSchedules.buildUseCaseObservable().test()
    }

    @Test
    fun testGetFlightSchedulesCallsRepository() {
        stubDataRepositoryGetFlightSchedules(Flowable.just(listOf(
                AirportFactory.makeScheduleEntity(),
                AirportFactory.makeScheduleEntity()
        )))

        val params = GetFlightSchedules.Params.forGetFlightSchedules("CAI", "RUH", "2019-01-01", 10, 1)
        mGetFlightSchedules.buildUseCaseObservable(params).test()

        verify(mockDataRepository).getFlightSchedules(anyString(), anyString(), anyString(), anyInt(), anyInt())
    }

    private fun stubDataRepositoryGetFlightSchedules(flowbale: Flowable<List<ScheduleEntity>>) {
        whenever(mockDataRepository.getFlightSchedules(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt(),
                ArgumentMatchers.anyInt()
        )).thenReturn(flowbale)
    }
}