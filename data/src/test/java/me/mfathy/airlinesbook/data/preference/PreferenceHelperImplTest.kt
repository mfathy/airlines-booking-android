package me.mfathy.airlinesbook.data.preference

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for PreferenceHelperImpl
 */
@RunWith(RobolectricTestRunner::class)
class PreferenceHelperImplTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var mPreferenceHelper: PreferenceHelperImpl

    @Before
    fun setUp() {
        mPreferenceHelper = PreferenceHelperImpl(RuntimeEnvironment.application.applicationContext)
    }

    @Test
    fun testSetAccessTokenSavesData() {
        val accessTokenEntity = AirportFactory.makeAccessTokenEntity()
        mPreferenceHelper.setAccessToken(accessTokenEntity)

        val savedAccessToken = mPreferenceHelper.getAccessToken()

        assertEqualsData(accessTokenEntity, savedAccessToken)
    }

    private fun assertEqualsData(entity: AccessTokenEntity, model: AccessTokenEntity) {
        assertEquals(entity.clintId, model.clintId)
        assertEquals(entity.tokenType, model.tokenType)
        assertEquals(entity.accessToken, model.accessToken)
        assertEquals(entity.expiresIn, model.expiresIn)
    }


}