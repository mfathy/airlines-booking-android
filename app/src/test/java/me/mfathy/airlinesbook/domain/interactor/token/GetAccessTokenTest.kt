package me.mfathy.airlinesbook.domain.interactor.token

import io.reactivex.Single
import me.mfathy.airlinesbook.ImmediateSchedulerRuleUnitTests
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.factory.AirportFactory
import me.mfathy.airlinesbook.factory.AirportFactory.makeAccessTokenParams
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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
 * Unit test for GetAccessToken
 */
@RunWith(MockitoJUnitRunner::class)
class GetAccessTokenTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var mGetAccessToken: GetAccessToken

    @Mock
    lateinit var mockDataRepository: AirportsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mGetAccessToken = GetAccessToken(mockDataRepository)
    }

    @Test
    fun testGetAccessTokenCompletes() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubGetAccessToken(Single.just(entity))

        val params = makeAccessTokenParams()

        mGetAccessToken.buildUseCaseObservable(params)
                .test()
                .assertComplete()
    }

    @Test
    fun testGetAccessTokenCallsRepository() {
        val entity = AirportFactory.makeAccessTokenEntity()
        stubGetAccessToken(Single.just(entity))

        val params = makeAccessTokenParams()

        mGetAccessToken.buildUseCaseObservable(params)
                .test()

        verify(mockDataRepository).getAccessToken(anyString(), anyString(), anyString())
    }

    private fun stubGetAccessToken(single: Single<AccessTokenEntity>) {
        `when`(mockDataRepository.getAccessToken(anyString(), anyString(), anyString())).thenReturn(single)
    }

}
