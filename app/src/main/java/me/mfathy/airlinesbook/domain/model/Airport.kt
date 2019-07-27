package me.mfathy.airlinesbook.domain.model

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
data class Airport(val name: String = "",
                   val airportCode: String = "",
                   val latitude: Double = 0.0,
                   val longitude: Double = 0.0,
                   val cityCode: String = "",
                   val countryCode: String = "",
                   val locationType: String = "")