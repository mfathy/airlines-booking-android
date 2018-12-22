package me.mfathy.airlinesbook.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Schedules recycler view adapter.
 */
class ScheduleAdapter @Inject constructor(val context: Context, val clickListener: (ScheduleEntity) -> Unit) : RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    var mSchedules: List<ScheduleEntity> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleAdapter.ViewHolder {
        val itemView = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_item_schedule, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mSchedules.count()
    }

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ScheduleAdapter.ViewHolder, position: Int) {
        val scheduleEntity = mSchedules[position]
        val mFlightAdapter = FlightAdapter(scheduleEntity) { clickListener(scheduleEntity) }
        holder.textViewDuration?.text = scheduleEntity.duration
        scheduleEntity.flights?.count()?.let {
            val stopsString = context.resources.getQuantityString(R.plurals.stops_plurals, it, it)
            holder.textViewStops?.text = stopsString
        }
        holder.rVSchedule?.layoutManager = LinearLayoutManager(context)
        scheduleEntity.flights?.let {
            mFlightAdapter.mFlights = it
        }
        holder.rVSchedule?.setHasFixedSize(true)
        holder.rVSchedule?.adapter = mFlightAdapter
        holder.cardViewSchedule?.setOnClickListener { clickListener(scheduleEntity) }
        holder.itemView.setOnClickListener { clickListener(scheduleEntity) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardViewSchedule: CardView? = view.findViewById(R.id.cardViewSchedule)
        var rVSchedule: RecyclerView? = view.findViewById(R.id.rVSchedules)
        var textViewDuration: TextView? = view.findViewById(R.id.textViewDuration)
        var textViewStops: TextView? = view.findViewById(R.id.textViewStops)
    }
}