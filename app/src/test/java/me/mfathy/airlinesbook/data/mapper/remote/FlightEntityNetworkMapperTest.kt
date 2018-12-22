package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.FlightEntity
import me.mfathy.airlinesbook.data.store.remote.model.FlightItem
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for FlightEntityNetworkMapper
 */
@RunWith(JUnit4::class)
class FlightEntityNetworkMapperTest {

    private val mapper = FlightEntityNetworkMapper()

    @Test(expected = UnsupportedOperationException::class)
    fun testMapFromEntityThrowsException() {
        mapper.mapFromEntity(AirportFactory.makeFlightEntity())
    }

    @Test
    fun testMapToEntityMapsData() {
        val flightItem = AirportFactory.makeFlightItem()
        val entity = mapper.mapToEntity(flightItem)

        assertEqualsData(entity, flightItem)
    }

    private fun assertEqualsData(entity: FlightEntity, flightItem: FlightItem) {
        assertEquals(entity.airlineId, flightItem.marketingCarrier.airlineID)
        assertEquals(entity.arrival.first, flightItem.arrival.scheduledTimeLocal.dateTime)
        assertEquals(entity.arrival.second.airportCode, AirportEntity(airportCode = flightItem.arrival.airportCode).airportCode)
        assertEquals(entity.departure.first, flightItem.departure.scheduledTimeLocal.dateTime)
        assertEquals(entity.departure.second.airportCode, AirportEntity(airportCode = flightItem.departure.airportCode).airportCode)
        assertEquals(entity.flightNumber, flightItem.marketingCarrier.flightNumber)
    }
}