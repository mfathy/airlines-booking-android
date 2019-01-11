package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.remote.model.Schedule
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for ScheduleEntityNetworkMapper
 */
@RunWith(JUnit4::class)
class ScheduleEntityNetworkMapperTest {

    private val flightMapper = FlightEntityNetworkMapper()
    private val mapper = ScheduleEntityNetworkMapper(flightMapper)

    @Test
    fun testMapToEntityMapsData() {
        val schedule = AirportFactory.makeSchedule()
        val entity = mapper.mapToEntity(schedule)

        assertEqualsData(entity, schedule)
    }

    private fun assertEqualsData(entity: ScheduleEntity, schedule: Schedule) {
        assertEquals(entity.duration, schedule.totalJourney.duration)
        assertEquals(entity.flights?.count(), schedule.flight?.count())
    }
}