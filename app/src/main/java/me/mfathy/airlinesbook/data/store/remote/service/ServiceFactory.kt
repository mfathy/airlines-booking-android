package me.mfathy.airlinesbook.data.store.remote.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.mfathy.airlinesbook.config.AppConstants.BASE_URL
import me.mfathy.airlinesbook.data.store.remote.model.AirportsResponse
import me.mfathy.airlinesbook.data.store.remote.model.FlightSchedulesResponse
import me.mfathy.airlinesbook.data.store.remote.model.deserializer.AirportDeserializer
import me.mfathy.airlinesbook.data.store.remote.model.deserializer.ScheduleDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Mohammed Fathy on 11/12/2018.
 * dev.mfathy@gmail.com
 *
 * ServiceFactory is a factory class which is responsible for creating Okhttp client and creates
 * an implementation of retrofit remote service.
 */
object ServiceFactory {

    fun makeAuthService(isDebug: Boolean): AuthServiceApi {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)), null)
        return makeAuthService(okHttpClient, Gson())
    }

    private fun makeAuthService(okHttpClient: OkHttpClient, mGson: Gson): AuthServiceApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build()

        return retrofit.create(AuthServiceApi::class.java)
    }

    fun makeRemoteService(isDebug: Boolean, oAuthInterceptor: OAuthInterceptor?): RemoteServiceApi {
        val okHttpClient = makeOkHttpClient(makeLoggingInterceptor((isDebug)), oAuthInterceptor)
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(FlightSchedulesResponse::class.java, ScheduleDeserializer())
        gsonBuilder.registerTypeAdapter(AirportsResponse::class.java, AirportDeserializer())
        val gson = gsonBuilder.create()
        return makeRemoteService(okHttpClient, gson)
    }

    private fun makeRemoteService(okHttpClient: OkHttpClient, gson: Gson): RemoteServiceApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(RemoteServiceApi::class.java)
    }

    private fun makeOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            oAuthInterceptor: OAuthInterceptor?
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(httpLoggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

        if (oAuthInterceptor != null)
            builder.addInterceptor(oAuthInterceptor)

        return builder.build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}