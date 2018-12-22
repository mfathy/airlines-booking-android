package me.mfathy.airlinesbook.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.FlightEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Flights recycler view adapter.
 */
class FlightAdapter @Inject constructor(
        private val scheduleEntity: ScheduleEntity,
        val clickListener: (ScheduleEntity) -> Unit) : RecyclerView.Adapter<FlightAdapter.ViewHolder>() {

    var mFlights: List<FlightEntity> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_item_flight, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mFlights.count()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val flightEntity = mFlights[position]
        holder.textViewAirportCodes?.text = "${flightEntity.departure.second.airportCode} - ${flightEntity.arrival.second.airportCode}"
        holder.textViewFlightNum?.text = "${flightEntity.airlineId}-${flightEntity.flightNumber}"
        holder.itemView.setOnClickListener { clickListener(scheduleEntity) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewAirportCodes: TextView? = view.findViewById(R.id.textViewAirportCodes)
        var textViewFlightNum: TextView? = view.findViewById(R.id.textViewFlightNum)
    }
}