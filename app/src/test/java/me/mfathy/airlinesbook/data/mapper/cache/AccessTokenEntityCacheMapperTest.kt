package me.mfathy.airlinesbook.data.mapper.cache

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 */
@RunWith(JUnit4::class)
class AccessTokenEntityCacheMapperTest {
    private val mapper = AccessTokenEntityCacheMapper()

    @Test
    fun testMapToEntityMapsData(){
        val cachedToken = AirportFactory.makeCachedAccessToken()
        val tokenEntity = mapper.mapToEntity(cachedToken)

        assertEqualData(tokenEntity, cachedToken)

    }

    @Test
    fun testMapFromEntityMapsData(){
        val entity = AirportFactory.makeAccessTokenEntity()
        val cachedAccessToken = mapper.mapFromEntity(entity)

        assertEqualData(entity, cachedAccessToken)
    }

    private fun assertEqualData(entity: AccessTokenEntity, domain: CachedAccessToken) {
        assertEquals(entity.accessToken, domain.accessToken)
        assertEquals(entity.tokenType, domain.tokenType)
        assertEquals(entity.clintId, domain.clientId)
        assertEquals(entity.expiresIn, domain.expiresIn)
    }
}