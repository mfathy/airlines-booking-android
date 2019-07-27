package me.mfathy.airlinesbook.domain.model

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
data class Schedule(val duration: String = "",
                    val flights: List<Flight>?)

data class Flight(val departure: Pair<String, Airport>,
                  val arrival: Pair<String, Airport>,
                  val flightNumber: Int = 0,
                  val airlineId: String = "")