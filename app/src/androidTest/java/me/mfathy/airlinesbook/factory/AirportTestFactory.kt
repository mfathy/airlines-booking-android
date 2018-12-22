package me.mfathy.airlinesbook.factory

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.FlightEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport
import me.mfathy.airlinesbook.data.store.remote.model.*

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Factory which will handle generating dummy data.
 */
object AirportTestFactory {
    fun makeAirportEntity(): AirportEntity {
        return AirportEntity(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomLong().toDouble(),
                RandomFactory.randomLong().toDouble(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString()
        )
    }

    fun makeScheduleEntity(): ScheduleEntity {
        return ScheduleEntity(
                RandomFactory.randomString(),
                listOf(makeFlightEntity())
        )
    }

    fun makeFlightEntity(): FlightEntity {
        return FlightEntity(
                Pair(first = RandomFactory.randomString(), second = makeAirportEntity()),
                Pair(first = RandomFactory.randomString(), second = makeAirportEntity()),
                RandomFactory.randomInt(),
                RandomFactory.randomString()
        )
    }

    fun makeAccessTokenEntity(): AccessTokenEntity {
        return AccessTokenEntity(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomInt().toLong()
        )
    }

    fun makeCachedAirport(): CachedAirport {
        return CachedAirport(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomLong().toDouble(),
                RandomFactory.randomLong().toDouble(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString()
        )
    }

    fun makeAccessToken(): AccessToken {
        return AccessToken(
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomString(),
                RandomFactory.randomInt().toLong()
        )
    }

    fun makeAirport(): Airport {
        return Airport(
                RandomFactory.randomString(),
                Names(name = Name(
                        RandomFactory.randomString(),
                        RandomFactory.randomString()
                )),
                RandomFactory.randomString(),
                Position(Coordinate(RandomFactory.randomLong().toDouble(), RandomFactory.randomLong().toDouble())),
                RandomFactory.randomString(),
                RandomFactory.randomString()
        )
    }

    fun makeFlightItem(): FlightItem {
        return FlightItem(
                departure = Departure(
                        airportCode = RandomFactory.randomString(),
                        scheduledTimeLocal = ScheduledTimeLocal(RandomFactory.randomString()),
                        terminal = Terminal("0")),
                marketingCarrier = MarketingCarrier(airlineID = RandomFactory.randomString()),
                arrival = Arrival(
                        airportCode = RandomFactory.randomString(),
                        scheduledTimeLocal = ScheduledTimeLocal(RandomFactory.randomString()),
                        terminal = Terminal("0"))
        )
    }

    fun makeSchedule(): Schedule {
        return Schedule(listOf(makeFlightItem()), TotalJourney(RandomFactory.randomString()))
    }

    fun makeCacheConfig(): CacheConfig {
        return CacheConfig(lastCacheTime = 0L)
    }

    fun makeAirportResponseString(): String {
        return "{\"AirportResource\": {\"Airports\": {\"Airport\": {\"AirportCode\": \"AAR\"}}}}"
    }

    fun makeAirportsResponseString(): String {
        return "{\"AirportResource\": {\"Airports\": {\"Airport\": [{\"AirportCode\": \"AAR\"},{\"AirportCode\": \"ABC\"}]}}}"
    }

    fun makeFlightScheduleResponseString(): String {
        return "{\"ScheduleResource\": {\"Schedule\": {\"TotalJourney\": {\"Duration\": \"P2\"}}}}"
    }

    fun makeFlightSchedulesResponseString(): String {
        return "{\"ScheduleResource\": {\"Schedule\": [{\"TotalJourney\": {\"Duration\": \"P2\"}},{\"TotalJourney\": {\"Duration\": \"P2\"}}]}}"
    }
}