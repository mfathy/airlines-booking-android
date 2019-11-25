package me.mfathy.airlinesbook.domain.model

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
data class Schedule(val duration: String = "",
                    val flights: List<Flight>?)
