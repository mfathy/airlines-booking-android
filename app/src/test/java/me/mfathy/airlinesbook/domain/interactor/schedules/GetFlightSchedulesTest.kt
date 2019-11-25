package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.test.tools.ImmediateSchedulerRuleUnitTests
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.repository.schedules.SchedulesRepository
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.AirportFactory.makeGetFlightSchedulesParams
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
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetFlightSchedules use case.
 */
@RunWith(MockitoJUnitRunner::class)
class GetFlightSchedulesTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = me.mfathy.test.tools.ImmediateSchedulerRuleUnitTests()

    private lateinit var mGetFlightSchedules: GetFlightSchedules

    @Mock
    lateinit var mockDataRepository: SchedulesRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetFlightSchedules = GetFlightSchedules(mockDataRepository)
    }

    @Test
    fun testGetFlightSchedulesCompletes() {
        val schedules = listOf(
                AirportFactory.makeScheduleEntity(),
                AirportFactory.makeScheduleEntity()
        )

        stubDataRepositoryGetFlightSchedules(schedules)

        val params = makeGetFlightSchedulesParams()
        mGetFlightSchedules.buildUseCaseObservable(params)
                .test()
                .assertComplete()
    }


    @Test
    fun testGetFlightSchedulesReturnsValue() {
        val schedules = listOf(
                AirportFactory.makeScheduleEntity(),
                AirportFactory.makeScheduleEntity()
        )

        stubDataRepositoryGetFlightSchedules(schedules)

        val params = makeGetFlightSchedulesParams()
        mGetFlightSchedules.buildUseCaseObservable(params)
                .test()
                .assertValue(schedules)
    }


    @Test
    fun testGetFlightSchedulesCallsRepository() {
        val schedules = listOf(
                AirportFactory.makeScheduleEntity(),
                AirportFactory.makeScheduleEntity()
        )

        stubDataRepositoryGetFlightSchedules(schedules)

        val params = makeGetFlightSchedulesParams()

        mGetFlightSchedules.buildUseCaseObservable(params)
                .test()

        verify(mockDataRepository)
                .getFlightSchedules(anyString(), anyString(), anyString(), anyInt(), anyInt())
    }

    private fun stubDataRepositoryGetFlightSchedules(schedules: List<ScheduleEntity>) {
        `when`(mockDataRepository.getFlightSchedules(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt()
        )).thenReturn(Flowable.just(schedules))
    }
}
