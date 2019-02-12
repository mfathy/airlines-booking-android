package me.mfathy.airlinesbook.domain.interactor.airports

import io.reactivex.Completable
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for ClearAirports use case.
 */
@RunWith(MockitoJUnitRunner::class)
class ClearAirportsTest {

    private lateinit var mClearAirports: ClearAirports

    @Mock
    lateinit var mockDataRepository: AirportsRepository
    @Mock
    lateinit var mockExecutionThread: ExecutionThread

    @Mock
    lateinit var mockSubscribeThread: SubscribeThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mClearAirports = ClearAirports(mockDataRepository, mockSubscribeThread, mockExecutionThread)
    }

    @Test
    fun testClearAirportsCompletes() {
        stubDataRepositoryClearAirports(Completable.complete())

        val testObserver = mClearAirports.buildUseCaseCompletable().test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAirportsCallsRepository() {
        stubDataRepositoryClearAirports(Completable.complete())

        mClearAirports.buildUseCaseCompletable().test()

        verify(mockDataRepository).clearAirports()
    }

    private fun stubDataRepositoryClearAirports(completable: Completable) {
        `when`(mockDataRepository.clearAirports()).thenReturn(completable)
    }
}
