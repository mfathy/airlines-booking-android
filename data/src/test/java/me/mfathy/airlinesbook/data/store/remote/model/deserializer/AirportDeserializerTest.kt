package me.mfathy.airlinesbook.data.store.remote.model.deserializer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.mfathy.airlinesbook.data.store.remote.model.AirportsResponse
import me.mfathy.airlinesbook.factory.AirportFactory
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Unit test for AirportDeserializer
 */
class AirportDeserializerTest {

    private val mGsonBuilder = GsonBuilder()

    private var mGson: Gson? = null

    @Before
    fun setUp() {
        mGsonBuilder.registerTypeAdapter(AirportsResponse::class.java, AirportDeserializer())
        mGson = mGsonBuilder.create()
    }

    @Test
    fun testDeserializeParsesJsonObject() {
        val jsonString = AirportFactory.makeAirportResponseString()
        val airportResponse = mGson?.fromJson(jsonString, AirportsResponse::class.java)
        assertEquals(airportResponse?.airportResource?.airports?.airportList?.count(), 1)
    }

    @Test
    fun testDeserializeParsesJsonArray() {
        val jsonString = AirportFactory.makeAirportsResponseString()
        val airportResponse = mGson?.fromJson(jsonString, AirportsResponse::class.java)
        assertEquals(airportResponse?.airportResource?.airports?.airportList?.count(), 2)
    }
}