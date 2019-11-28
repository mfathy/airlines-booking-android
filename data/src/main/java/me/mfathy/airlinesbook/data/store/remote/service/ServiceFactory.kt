package me.mfathy.airlinesbook.data.store.remote.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.mfathy.airlinesbook.data.config.AppConstants.BASE_URL
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

    fun provideAuthService(debugMode: Boolean): AuthServiceApi {
        val loggingInterceptor = provideLoggingInterceptor((debugMode))
        val okHttpClient = provideOkHttpClient(loggingInterceptor, null)
        return provideAuthService(okHttpClient, Gson())
    }

    fun provideRemoteService(debugMode: Boolean, oAuthInterceptor: OAuthInterceptor): RemoteServiceApi {
        val loggingInterceptor = provideLoggingInterceptor((debugMode))
        val okHttpClient = provideOkHttpClient(loggingInterceptor, oAuthInterceptor)
        val gsonBuilder = provideGsonBuilder()
        gsonBuilder.registerTypeAdapter(FlightSchedulesResponse::class.java, ScheduleDeserializer())
        gsonBuilder.registerTypeAdapter(AirportsResponse::class.java, AirportDeserializer())
        val gson = gsonBuilder.create()
        return provideRemoteService(okHttpClient, gson)
    }

    private fun provideAuthService(okHttpClient: OkHttpClient, gson: Gson): AuthServiceApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(AuthServiceApi::class.java)
    }

    private fun provideRemoteService(okHttpClient: OkHttpClient, gson: Gson): RemoteServiceApi {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        return retrofit.create(RemoteServiceApi::class.java)
    }

    private fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, oAuthInterceptor: OAuthInterceptor?): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

        if (oAuthInterceptor != null)
            builder.addInterceptor(oAuthInterceptor)

        return builder.build()
    }

    private fun provideLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    private fun provideGsonBuilder(): GsonBuilder = GsonBuilder()
}