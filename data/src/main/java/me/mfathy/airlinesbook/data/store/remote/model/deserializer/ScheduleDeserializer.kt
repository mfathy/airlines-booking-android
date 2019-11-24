package me.mfathy.airlinesbook.data.store.remote.model.deserializer

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import me.mfathy.airlinesbook.data.store.remote.model.FlightSchedulesResponse
import me.mfathy.airlinesbook.data.store.remote.model.Schedule
import me.mfathy.airlinesbook.data.store.remote.model.ScheduleResource
import java.lang.reflect.Type


/**
 * Created by Mohammed Fathy on 20/12/2018.
 * dev.mfathy@gmail.com
 *
 * ScheduleDeserializer is a custom deserializer to dynamic parsing of FlightSchedulesResponse.
 */
class ScheduleDeserializer : JsonDeserializer<FlightSchedulesResponse> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): FlightSchedulesResponse {
        val asJsonObject = json.asJsonObject
        val scheduleResourceObj = asJsonObject.getAsJsonObject("ScheduleResource")

        var schedules: MutableList<Schedule> = mutableListOf()
        when {
            scheduleResourceObj.get("Schedule").isJsonArray -> {
                val schedulesString = scheduleResourceObj.getAsJsonArray("Schedule")
                val schedulesType = object : TypeToken<MutableList<Schedule>>() {}.type
                schedules = Gson().fromJson<MutableList<Schedule>>(schedulesString, schedulesType)
            }
            scheduleResourceObj.get("Schedule").isJsonObject -> {
                val scheduleString = scheduleResourceObj.getAsJsonObject("Schedule")
                val schedule = Gson().fromJson(scheduleString, Schedule::class.java)
                schedules.add(schedule)
            }
        }

        return FlightSchedulesResponse(ScheduleResource((schedules)))
    }

}