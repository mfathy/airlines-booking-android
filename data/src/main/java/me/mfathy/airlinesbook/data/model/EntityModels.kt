package me.mfathy.airlinesbook.data.model

import android.os.Parcel
import android.os.Parcelable


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
                             val expiresIn: Long = 0)

data class AirportEntity(val name: String = "",
                         val airportCode: String = "",
                         val latitude: Double = 0.0,
                         val longitude: Double = 0.0,
                         val cityCode: String = "",
                         val countryCode: String = "",
                         val locationType: String = "") : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!)


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(airportCode)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeString(cityCode)
        parcel.writeString(countryCode)
        parcel.writeString(locationType)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AirportEntity> {
        override fun createFromParcel(parcel: Parcel): AirportEntity {
            return AirportEntity(parcel)
        }

        override fun newArray(size: Int): Array<AirportEntity?> {
            return arrayOfNulls(size)
        }
    }
}

data class ScheduleEntity(val duration: String = "",
                          val flights: List<FlightEntity>?)

data class FlightEntity(val departure: Pair<String, AirportEntity>,
                        val arrival: Pair<String, AirportEntity>,
                        val flightNumber: Int = 0,
                        val airlineId: String = "")