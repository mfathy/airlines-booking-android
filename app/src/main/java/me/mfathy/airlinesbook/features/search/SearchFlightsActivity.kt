package me.mfathy.airlinesbook.features.search

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tapadoo.alerter.Alerter
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search_flights.*
import kotlinx.android.synthetic.main.bottom_sheet_search_flights.*
import kotlinx.android.synthetic.main.content_search_flights.*
import me.mfathy.airlinesbook.BuildConfig
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.exceptions.ErrorMessageFactory
import me.mfathy.airlinesbook.injection.ViewModelFactory
import me.mfathy.airlinesbook.features.details.FlightDetailsActivity
import me.mfathy.airlinesbook.features.details.FlightDetailsActivity.Companion.SELECTED_SCHEDULE
import me.mfathy.airlinesbook.features.select.SelectionActivity
import me.mfathy.airlinesbook.features.select.SelectionActivity.Companion.PICK_DESTINATION_AIRPORT_REQUEST
import me.mfathy.airlinesbook.features.select.SelectionActivity.Companion.PICK_ORIGIN_AIRPORT_REQUEST
import me.mfathy.airlinesbook.features.select.SelectionActivity.Companion.SELECTED_AIRPORT_RESULT_KEY
import me.mfathy.airlinesbook.features.state.Resource
import me.mfathy.airlinesbook.features.state.ResourceState
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * SearchFlightsActivity shows a list of flight result.
 */
class SearchFlightsActivity : AppCompatActivity(), DatePickerListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var searchViewModel: SearchViewModel

    private lateinit var mScheduleAdapter: ScheduleAdapter

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_flights)
        AndroidInjection.inject(this)
        setSupportActionBar(toolbar)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        flightDate.setText(getDateString(Date()))

        searchViewModel = ViewModelProvider(this, viewModelFactory)
                .get(SearchViewModel::class.java)

        buttonSearchFlights.setOnClickListener {

            val origin = originAirportCode.text.toString()
            val destination = destinationAirportCode.text.toString()
            val flightDateString = flightDate.text.toString()
            val limit = 100
            val offset = 1
            searchViewModel.fetchFlightSchedules(origin, destination, flightDateString, limit, offset)
        }

        originLayout.setOnClickListener {
            startActivityForResult(
                    SelectionActivity.getStartIntent(this@SearchFlightsActivity),
                    PICK_ORIGIN_AIRPORT_REQUEST
            )
        }

        destinationLayout.setOnClickListener {
            startActivityForResult(
                    SelectionActivity.getStartIntent(this@SearchFlightsActivity),
                    PICK_DESTINATION_AIRPORT_REQUEST
            )
        }

        imageViewSwitch.setOnClickListener {
            val origAirportCode = originAirportCode.text
            val origAirportName = originAirportName.text
            val destAirportCode = destinationAirportCode.text
            val destAirportName = destinationAirportName.text

            originAirportCode.text = destAirportCode
            originAirportName.text = destAirportName

            destinationAirportCode.text = origAirportCode
            destinationAirportName.text = origAirportName
        }

    }

    override fun onStart() {
        super.onStart()

        searchViewModel.getSchedulesLiveData().observe(this,
                Observer<Resource<List<ScheduleEntity>>> {
                    it?.let { resource ->
                        handleFlightSchedulesResult(resource)
                    }
                })

        searchViewModel.getAccessTokenLiveData().observe(this,
                Observer<Resource<AccessTokenEntity>> {
                    it?.let { resource ->
                        handleAccessTokenResponse(resource)
                    }
                })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_ORIGIN_AIRPORT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val airport = it.getParcelableExtra<AirportEntity>(SELECTED_AIRPORT_RESULT_KEY)
                    originAirportCode.text = airport.airportCode
                    originAirportName.text = airport.name
                }
            }
        }

        if (requestCode == PICK_DESTINATION_AIRPORT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    val airport = it.getParcelableExtra<AirportEntity>(SELECTED_AIRPORT_RESULT_KEY)
                    destinationAirportCode.text = airport.airportCode
                    destinationAirportName.text = airport.name
                }
            }
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun handleFlightSchedulesResult(resource: Resource<List<ScheduleEntity>>) {
        animateBottomSheet(bottomSheetBehavior, 300)
        when (resource.status) {
            ResourceState.LOADING -> {
                recyclerView.visibility = View.INVISIBLE
                textViewScheduleError.visibility = View.INVISIBLE
                progressBarLoading.visibility = View.VISIBLE
            }
            ResourceState.ERROR -> {
                resource.error?.let {
                    recyclerView.visibility = View.INVISIBLE
                    progressBarLoading.visibility = View.INVISIBLE
                    textViewScheduleError.visibility = View.VISIBLE
                    textViewScheduleError.text = ErrorMessageFactory.create(this, it)
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            ResourceState.SUCCESS -> {
                resource.data?.let { schedules ->
                    mScheduleAdapter = ScheduleAdapter(this@SearchFlightsActivity) { onScheduleClicked(it) }
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    mScheduleAdapter.mSchedules = schedules
                    recyclerView.adapter = mScheduleAdapter
                    recyclerView.visibility = View.VISIBLE
                    progressBarLoading.visibility = View.INVISIBLE
                    textViewScheduleError.visibility = View.INVISIBLE
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    private fun onScheduleClicked(scheduleEntity: ScheduleEntity) {
        val startIntent = FlightDetailsActivity.getStartIntent(this)
        val flightsArray: MutableList<Pair<String, String>> = mutableListOf()

        scheduleEntity.flights?.forEach {
            flightsArray.add(Pair(
                    first = it.departure.second.airportCode,
                    second = it.arrival.second.airportCode))
        }
        startIntent.putExtra(SELECTED_SCHEDULE, flightsArray.toTypedArray())
        startActivity(startIntent)
    }

    private fun handleAccessTokenResponse(resource: Resource<AccessTokenEntity>) {
        when (resource.status) {
            ResourceState.LOADING -> {
                showLoading()
            }
            ResourceState.ERROR -> {
                resource.error?.let {
                    textViewScheduleError.text = ErrorMessageFactory.create(this, it)
                    hideLoading()
                }
            }
            ResourceState.SUCCESS -> {
                hideLoading()
                resource.data?.let {
                    Log.d(TAG, it.toString())
                }
            }
        }
    }

    private fun showLoading() {
        Alerter.create(this@SearchFlightsActivity)
                .setTitle(getString(R.string.authenticating))
                .setText(getString(R.string.tap_to_dismiss))
                .enableProgress(true)
                .setProgressColorRes(R.color.colorAccent)
                .show()
    }

    private fun hideLoading() {
        Alerter.hide()
    }

    private fun getDateString(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(date)
    }

    fun showDatePickerDialog(@Suppress("UNUSED_PARAMETER") v: View) {
        val newFragment = DatePickerFragment()
        newFragment.listener = this
        newFragment.show(supportFragmentManager, "datePicker")
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        flightDate.setText("$year-$month-$day")
    }

    /**
     * Animated bottom sheet start up >> Slide from bottom or up.
     */
    private fun animateBottomSheet(sheetBehavior: BottomSheetBehavior<*>, newPeekHeight: Int) {
        val anim = ValueAnimator.ofInt(sheetBehavior.peekHeight, newPeekHeight)
        anim.addUpdateListener { valueAnimator ->
            val peekHeight = valueAnimator.animatedValue as Int
            sheetBehavior.setPeekHeight(peekHeight)
        }
        anim.duration = 500
        anim.start()

        sheetBehavior.isHideable = false
    }

    companion object {
        const val TAG = "SearchFlightsActivity"
    }

}
