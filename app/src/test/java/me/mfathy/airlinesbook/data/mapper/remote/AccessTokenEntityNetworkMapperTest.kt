package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import me.mfathy.airlinesbook.data.store.remote.model.AccessToken
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.UnsupportedOperationException

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
@RunWith(JUnit4::class)
class AccessTokenEntityNetworkMapperTest {
    private val mapper = AccessTokenEntityNetworkMapper()

    @Test(expected = UnsupportedOperationException::class)
    fun testMapFromEntityThrowsException() {
        mapper.mapFromEntity(AirportFactory.makeAccessTokenEntity())
    }
    @Test
    fun testMapToEntityMapsData(){
        val token = AirportFactory.makeAccessToken()
        val entity = mapper.mapToEntity(token)

        assertEqualData(entity, token)
    }

    private fun assertEqualData(entity: AccessTokenEntity, domain: AccessToken) {
        assertEquals(entity.accessToken, domain.accessToken)
        assertEquals(entity.tokenType, domain.tokenType)
        assertEquals(entity.clintId, domain.clientId)
        assertEquals(entity.expiresIn, domain.expiresIn)
    }
}