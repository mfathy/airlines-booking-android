package me.mfathy.airlinesbook.data.store.remote.service

import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.remote.model.AccessToken
import me.mfathy.airlinesbook.data.store.remote.model.AirportsResponse
import me.mfathy.airlinesbook.data.store.remote.model.FlightSchedulesResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * RemoteService retrofit end point to access remote server api.
 */
interface RemoteService {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("grant_type") grantType: String): Single<AccessToken>

    @GET("")
    fun getAirports(lang: String, limit: Int, offset: Int): Single<AirportsResponse>

    @GET("")
    fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Single<FlightSchedulesResponse>

}