package me.mfathy.airlinesbook.domain.model

data class Flight(val departure: Pair<String, Airport>,
                  val arrival: Pair<String, Airport>,
                  val flightNumber: Int = 0,
                  val airlineId: String = "")