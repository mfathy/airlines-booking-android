package me.mfathy.airlinesbook.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.schedules.GetScheduleFlightDetails
import me.mfathy.airlinesbook.ui.state.Resource
import me.mfathy.airlinesbook.ui.state.ResourceState
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * FlightDetailsViewModel is the view model to run GetScheduleFlightDetails use case and handle
 * its response.
 */
open class FlightDetailsViewModel @Inject internal constructor(
        private val getScheduleFlightDetails: GetScheduleFlightDetails
) : ViewModel() {
    private val airportsLiveData: MutableLiveData<Resource<List<AirportEntity>>> = MutableLiveData()


    override fun onCleared() {
        getScheduleFlightDetails.dispose()
        super.onCleared()
    }

    fun getAirportsLiveData(): LiveData<Resource<List<AirportEntity>>> {
        return airportsLiveData
    }

    fun fetchAirports(airportCodes: List<Pair<String, String>>, lang: String, limit: Int, offset: Int) {
        airportsLiveData.postValue(Resource(ResourceState.LOADING, null, null, null))
        getScheduleFlightDetails.execute(AirportsSubscriber(),
                GetScheduleFlightDetails.Params.forGetScheduleFlightDetails(
                        airportCodes,
                        lang,
                        limit,
                        offset
                ))

    }

    inner class AirportsSubscriber : DisposableSubscriber<List<AirportEntity>>() {
        override fun onComplete() {}

        override fun onNext(airports: List<AirportEntity>) {
            airportsLiveData.postValue(Resource(ResourceState.SUCCESS, airports, null, null))
        }

        override fun onError(e: Throwable) {
            airportsLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage, e))
        }
    }

}