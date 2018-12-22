package me.mfathy.airlinesbook.domain.interactor.token

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.DataFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetAccessToken
 */
@RunWith(MockitoJUnitRunner::class)
class GetAccessTokenTest {

    private lateinit var mGetAccessToken: GetAccessToken

    @Mock
    lateinit var mockDataRepository: AirportsRepository
    @Mock
    lateinit var mockExecutionThread: ExecutionThread
    @Mock
    lateinit var mockSubscribeThread: SubscribeThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetAccessToken = GetAccessToken(mockDataRepository, mockSubscribeThread, mockExecutionThread)
    }

    @Test
    fun testGetAccessTokenCompletes() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubGetAccessToken(Single.just(entity))

        val params = GetAccessToken.Params.forGetAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
        val testObserver = mGetAccessToken.buildUseCaseObservable(params).test()
        testObserver.assertComplete()
    }

    @Test
    fun testGetAccessTokenCallsRepository() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubGetAccessToken(Single.just(entity))

        val params = GetAccessToken.Params.forGetAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
        mGetAccessToken.buildUseCaseObservable(params).test()
        verify(mockDataRepository).getAccessToken(any(), any(), any())
    }

    @Test(expected = IllegalArgumentException::class)
    fun testGetAccessTokenThrowsException() {
        mGetAccessToken.buildUseCaseObservable().test()
    }

    private fun stubGetAccessToken(single: Single<AccessTokenEntity>) {
        whenever(mockDataRepository.getAccessToken(any(), any(), any())).thenReturn(single)
    }

}