package me.mfathy.airlinesbook.data.store.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * NetworkModels: a kotlin file that contains all the remote api network models that is required by
 * the RemoteDataStore.
 */

//region GetAccessToken >> models
data class AccessToken(
        val clientId: String,
        @SerializedName("access_token")
        val accessToken: String = "",
        @SerializedName("token_type")
        val tokenType: String = "",
        @SerializedName("expires_in")
        val expiresIn: Int = 0)
//endregion

//region GetAirports >> models

data class AirportsResponse(@SerializedName("AirportResource")
                            val airportResource: AirportResource)


data class AirportResource(@SerializedName("Airports")
                           val airports: Airports)

data class Names(@SerializedName("Name")
                 val name: Name)


data class Coordinate(@SerializedName("Latitude")
                      val latitude: Double = 0.0,
                      @SerializedName("Longitude")
                      val longitude: Double = 0.0)


data class Position(@SerializedName("Coordinate")
                    val coordinate: Coordinate)


data class Airport(@SerializedName("CityCode")
                   val cityCode: String = "",
                   @SerializedName("Names")
                   val names: Names,
                   @SerializedName("AirportCode")
                   val airportCode: String = "",
                   @SerializedName("Position")
                   val position: Position,
                   @SerializedName("CountryCode")
                   val countryCode: String = "",
                   @SerializedName("LocationType")
                   val locationType: String = "")


data class Airports(@SerializedName("Airport")
                    val airportList: List<Airport>)


data class Name(@SerializedName("@LanguageCode")
                val languageCode: String = "",
                @SerializedName("$")
                val title: String = "")
//endregion

//region GetFlightSchedules >> models
data class FlightSchedulesResponse(@SerializedName("ScheduleResource")
                                   val scheduleResource: ScheduleResource)


data class ScheduledTimeLocal(@SerializedName("DateTime")
                              val dateTime: String = "")


data class Departure(@SerializedName("AirportCode")
                     val airportCode: String = "",
                     @SerializedName("ScheduledTimeLocal")
                     val scheduledTimeLocal: ScheduledTimeLocal,
                     @SerializedName("Terminal")
                     val terminal: Terminal)


data class MarketingCarrier(@SerializedName("AirlineID")
                            val airlineID: String = "",
                            @SerializedName("FlightNumber")
                            val flightNumber: Int = 0)


data class Schedule(@SerializedName("Flight")
                    val flight: List<FlightItem>?,
                    @SerializedName("TotalJourney")
                    val totalJourney: TotalJourney)


data class TotalJourney(@SerializedName("Duration")
                        val duration: String = "")


data class ScheduleResource(@SerializedName("Schedule")
                            val schedule: List<Schedule>)


data class Arrival(@SerializedName("AirportCode")
                   val airportCode: String = "",
                   @SerializedName("ScheduledTimeLocal")
                   val scheduledTimeLocal: ScheduledTimeLocal,
                   @SerializedName("Terminal")
                   val terminal: Terminal)


data class Terminal(@SerializedName("Name")
                    val name: Int = 0)


data class FlightItem(@SerializedName("Departure")
                      val departure: Departure,
                      @SerializedName("MarketingCarrier")
                      val marketingCarrier: MarketingCarrier,
                      @SerializedName("Arrival")
                      val arrival: Arrival)
//endregion