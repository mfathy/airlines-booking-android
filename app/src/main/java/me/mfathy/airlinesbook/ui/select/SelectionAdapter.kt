package me.mfathy.airlinesbook.ui.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AirportEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Airports recycler view adapter
 */
class SelectionAdapter @Inject constructor(val clickListener: (AirportEntity) -> Unit)
    : RecyclerView.Adapter<SelectionAdapter.ViewHolder>() {

    var mAirports: List<AirportEntity> = arrayListOf()
    var mAirportsFiltered: List<AirportEntity> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionAdapter.ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_item_airport, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mAirports.count()
    }

    override fun onBindViewHolder(holder: SelectionAdapter.ViewHolder, position: Int) {
        val airport = mAirports[position]
        holder.textViewAirportCode?.text = airport.airportCode
        holder.textViewCityName?.text = airport.name

        holder.itemView.setOnClickListener { clickListener(airport) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewAirportCode: TextView? = view.findViewById(R.id.textViewAirportCode)
        var textViewCityName: TextView? = view.findViewById(R.id.textViewCityName)
    }
}