package me.mfathy.airlinesbook.data.model


/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * EntityModels: a kotlin file that contains all the data layer models required to be used by all
 * application layers like domain and ui layer.
 */

data class AccessTokenEntity(val clintId: String = "",
                             val accessToken: String = "",
                             val tokenType: String = "",
                             val expiresIn: Int = 0)

data class AirportEntity(val name: String = "",
                         val airportCode: String = "",
                         val latitude: Double = 0.0,
                         val longitude: Double = 0.0,
                         val cityCode: String = "",
                         val countryCode: String = "",
                         val locationType: String = "")

data class ScheduleEntity(val duration: String = "",
                          val flights:List<FlightEntity>?)

data class FlightEntity(val departure: Pair<String, AirportEntity>,
                        val arrival: Pair<String, AirportEntity>,
                        val flightNumber: Int = 0,
                        val airlineId: String = "")