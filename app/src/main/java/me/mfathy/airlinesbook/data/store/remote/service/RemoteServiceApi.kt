package me.mfathy.airlinesbook.data.store.remote.service

import io.reactivex.Single
import me.mfathy.airlinesbook.data.store.remote.model.AirportsResponse
import me.mfathy.airlinesbook.data.store.remote.model.FlightSchedulesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * RemoteServiceApi retrofit end point to access remote server api which has token access.
 */
interface RemoteServiceApi {

    @GET("references/airports")
    fun getAirports(
            @Query("lang") lang: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int): Single<AirportsResponse>

    @GET("operations/schedules/{origin}/{dest}/{date}")
    fun getFlightSchedules(@Path("origin") origin: String,
                           @Path("dest") destination: String,
                           @Path("date") dateOfTravel: String,
                           @Query("limit") limit: Int,
                           @Query("offset") offset: Int): Single<FlightSchedulesResponse>

    @GET("references/airports/{airportCode}")
    fun getAirport(@Path("airportCode") airportCode: String,
                   @Query("lang") lang: String,
                   @Query("limit") limit: Int,
                   @Query("offset") offset: Int): Single<AirportsResponse>

}