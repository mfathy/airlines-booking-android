package me.mfathy.airlinesbook.features.details

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.tapadoo.alerter.Alerter
import dagger.android.AndroidInjection
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.exceptions.ErrorMessageFactory
import me.mfathy.airlinesbook.injection.factories.ViewModelFactory
import me.mfathy.airlinesbook.features.state.Resource
import me.mfathy.airlinesbook.features.state.ResourceState
import java.util.*
import javax.inject.Inject

/**
 * FlightDetailsActivity show a map with a polyline from the origin airport to the destination
 * airport.
 */
class FlightDetailsActivity : AppCompatActivity(), OnMapReadyCallback {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var flightDetailsViewModel: FlightDetailsViewModel
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_details)
        AndroidInjection.inject(this)

        val flightsArray: Array<Pair<String, String>> = intent.getSerializableExtra(SELECTED_SCHEDULE) as Array<Pair<String, String>>
        title = "${flightsArray.first().first} - ${flightsArray.last().second}"

        flightDetailsViewModel = ViewModelProvider(this, viewModelFactory)
                .get(FlightDetailsViewModel::class.java)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()

        flightDetailsViewModel.getAirportsLiveData().observe(this,
                Observer<Resource<List<AirportEntity>>> {
                    it?.let { resource ->
                        handleAirportCodesResult(resource)
                    }
                })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun handleAirportCodesResult(resource: Resource<List<AirportEntity>>) {
        val flightsArray: Array<Pair<String, String>> = intent.getSerializableExtra(SELECTED_SCHEDULE) as Array<Pair<String, String>>
        when (resource.status) {
            ResourceState.LOADING -> {
                showLoading()
            }
            ResourceState.SUCCESS -> {
                resource.data?.let { airportResult ->
                    val mapResult: Map<String, AirportEntity> = airportResult.map { it.airportCode to it }.toMap()

                    val polylineOptions = PolylineOptions()
                            .startCap(RoundCap())
                            .endCap(RoundCap())
                            .geodesic(true)
                            .width(10.0f)
                            .color(ContextCompat.getColor(this, R.color.colorAccent))

                    flightsArray.forEach {
                        val firstLatitude = mapResult[it.first]?.latitude
                        val firstLongitude = mapResult[it.first]?.longitude

                        val secondLatitude = mapResult[it.second]?.latitude
                        val secondLongitude = mapResult[it.second]?.longitude

                        if (firstLatitude != null && firstLongitude != null) polylineOptions.add(LatLng(firstLatitude, firstLongitude))
                        if (secondLatitude != null && secondLongitude != null) polylineOptions.add(LatLng(secondLatitude, secondLongitude))

                    }

                    mMap.addPolyline(polylineOptions)
                    mMap.addMarker(MarkerOptions()
                            .position(polylineOptions.points.first())
                            .title(flightsArray.first().first)
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_point_start)))
                    ).showInfoWindow()

                    mMap.addMarker(MarkerOptions()
                            .position(polylineOptions.points.last())
                            .title(flightsArray.last().second)
                            .anchor(0.5f, 0.5f)
                            .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.drawable.ic_point_end)))
                    )

                    val bounds = LatLngBounds.Builder()
                            .include(polylineOptions.points.first())
                            .include(polylineOptions.points.last())

                    val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds.build(), 250)

                    mMap.animateCamera(cameraUpdate)
                    mMap.setMinZoomPreference(2.0f)
                    hideLoading()
                }
            }
            ResourceState.ERROR -> {
                resource.error?.let {
                    Alerter.create(this@FlightDetailsActivity)
                            .setText(ErrorMessageFactory.create(this, it))
                            .show()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val flightsArray: Array<Pair<String, String>> = intent.getSerializableExtra(SELECTED_SCHEDULE) as Array<Pair<String, String>>
        val lang = Locale.getDefault().language
        flightDetailsViewModel.fetchAirports(flightsArray.toList(), lang, 100, 1)
    }

    private fun showLoading() {
        Alerter.create(this@FlightDetailsActivity)
                .setTitle(getString(R.string.loading))
                .setText(getString(R.string.tap_to_dismiss))
                .enableProgress(true)
                .setProgressColorRes(R.color.colorAccent)
                .show()
    }

    private fun hideLoading() {
        Alerter.hide()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, FlightDetailsActivity::class.java)
        }

        const val SELECTED_SCHEDULE = "SELECTED_SCHEDULE"
    }
}
