package me.mfathy.airlinesbook.data.store.remote.model.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import me.mfathy.airlinesbook.data.store.remote.model.Airport
import me.mfathy.airlinesbook.data.store.remote.model.AirportResource
import me.mfathy.airlinesbook.data.store.remote.model.Airports
import me.mfathy.airlinesbook.data.store.remote.model.AirportsResponse
import java.lang.reflect.Type


/**
 * Created by Mohammed Fathy on 20/12/2018.
 * dev.mfathy@gmail.com
 *
 * AirportDeserializer is a custom deserializer to handle dynamic parsing of AirportsResponse
 */
class AirportDeserializer : JsonDeserializer<AirportsResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): AirportsResponse {
        val asJsonObject = json.asJsonObject
        val responseResourceObj = asJsonObject.getAsJsonObject("AirportResource")
        val airportJson = responseResourceObj.getAsJsonObject("Airports")
        var airports: MutableList<Airport> = mutableListOf()
        when {
            airportJson.get("Airport").isJsonArray -> {
                val airportsString = airportJson.getAsJsonArray("Airport")
                val airportsType = object : TypeToken<MutableList<Airport>>() {}.type
                airports = Gson().fromJson<MutableList<Airport>>(airportsString, airportsType)
            }
            airportJson.get("Airport").isJsonObject -> {
                val airportString = airportJson.getAsJsonObject("Airport")
                val airport = Gson().fromJson(airportString, Airport::class.java)
                airports.add(airport)
            }
        }

        return AirportsResponse(AirportResource(Airports(airports)))
    }

}