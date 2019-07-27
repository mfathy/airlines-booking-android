package me.mfathy.airlinesbook.data.mapper.cache

import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportEntityCacheMapper
 */
@RunWith(JUnit4::class)
class AirportEntityCacheMapperTest {

    private val mapper = AirportEntityCacheMapper()

    @Test
    fun testMapFromEntityMapsData() {
        val entity = AirportFactory.makeAirportEntity()
        val cachedAirport = mapper.mapFromEntity(entity)

        assertEqualData(entity, cachedAirport)
    }

    private fun assertEqualData(entity: AirportEntity, domain: CachedAirport) {
        assertEquals(entity.airportCode, domain.airportCode)
        assertEquals(entity.cityCode, domain.cityCode)
        assertEquals(entity.countryCode, domain.countryCode)
        assertEquals(entity.latitude, domain.latitude, 0.1)
        assertEquals(entity.longitude, domain.longitude, 0.1)
        assertEquals(entity.locationType, domain.locationType)
        assertEquals(entity.name, domain.name)
    }
}