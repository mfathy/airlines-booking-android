package me.mfathy.airlinesbook.factory

import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.FlightEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.local.models.CacheConfig
import me.mfathy.airlinesbook.data.store.local.models.CachedAccessToken
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport
import me.mfathy.airlinesbook.data.store.remote.model.*

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
object AirportFactory {
    fun makeAirportEntity(): AirportEntity {
        return AirportEntity(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomLong().toDouble(),
                DataFactory.randomLong().toDouble(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
    }

    fun makeScheduleEntity(): ScheduleEntity {
        return ScheduleEntity(
                DataFactory.randomString(),
                listOf(makeFlightEntity())
        )
    }

    fun makeFlightEntity(): FlightEntity {
        return FlightEntity(
                Pair(first = DataFactory.randomString(), second = makeAirportEntity()),
                Pair(first = DataFactory.randomString(), second = makeAirportEntity()),
                DataFactory.randomInt(),
                DataFactory.randomString()
        )
    }

    fun makeCachedAccessToken(): CachedAccessToken {
        return CachedAccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomInt()

        )
    }

    fun makeAccessTokenEntity(): AccessTokenEntity {
        return AccessTokenEntity(
                "test1",
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomInt()
        )
    }

    fun makeCachedAirport(): CachedAirport {
        return CachedAirport(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomLong().toDouble(),
                DataFactory.randomLong().toDouble(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
    }

    fun makeAccessToken(): AccessToken {
        return AccessToken(
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomString(),
                DataFactory.randomInt()
        )
    }

    fun makeAirport(): Airport {
        return Airport(
                DataFactory.randomString(),
                Names(name = Name(
                        DataFactory.randomString(),
                        DataFactory.randomString()
                )),
                DataFactory.randomString(),
                Position(Coordinate(DataFactory.randomLong().toDouble(), DataFactory.randomLong().toDouble())),
                DataFactory.randomString(),
                DataFactory.randomString()
        )
    }

    fun makeFlightItem(): FlightItem {
        return FlightItem(
                departure = Departure(
                        airportCode = DataFactory.randomString(),
                        scheduledTimeLocal = ScheduledTimeLocal(DataFactory.randomString()),
                        terminal = Terminal(0)),
                marketingCarrier = MarketingCarrier(airlineID = DataFactory.randomString()),
                arrival = Arrival(
                        airportCode = DataFactory.randomString(),
                        scheduledTimeLocal = ScheduledTimeLocal(DataFactory.randomString()),
                        terminal = Terminal(0))
        )
    }

    fun makeSchedule(): Schedule {
        return Schedule(listOf(makeFlightItem()), TotalJourney(DataFactory.randomString()))
    }

    fun makeCacheConfig(): CacheConfig {
        return CacheConfig(lastCacheTime = 0L)
    }
}