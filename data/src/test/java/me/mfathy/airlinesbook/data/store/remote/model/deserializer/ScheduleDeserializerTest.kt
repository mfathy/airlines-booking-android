package me.mfathy.airlinesbook.data.store.remote.model.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.mfathy.airlinesbook.data.store.remote.model.FlightSchedulesResponse
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for ScheduleDeserializer
 */
class ScheduleDeserializerTest {


    private val mGsonBuilder = GsonBuilder()

    private var mGson: Gson? = null

    @Before
    fun setUp() {
        mGsonBuilder.registerTypeAdapter(FlightSchedulesResponse::class.java, ScheduleDeserializer())
        mGson = mGsonBuilder.create()
    }

    @Test
    fun testDeserializeParsesJsonObject() {
        val jsonString = AirportFactory.makeFlightScheduleResponseString()
        val response = mGson?.fromJson(jsonString, FlightSchedulesResponse::class.java)
        assertEquals(response?.scheduleResource?.schedule?.count(), 1)
    }

    @Test
    fun testDeserializeParsesJsonArray() {
        val jsonString = AirportFactory.makeFlightSchedulesResponseString()
        val response = mGson?.fromJson(jsonString, FlightSchedulesResponse::class.java)
        assertEquals(response?.scheduleResource?.schedule?.count(), 2)
    }
}