package me.mfathy.airlinesbook.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.domain.interactor.schedules.GetFlightSchedules
import me.mfathy.airlinesbook.domain.interactor.token.GetAccessToken
import me.mfathy.airlinesbook.ui.state.Resource
import me.mfathy.airlinesbook.ui.state.ResourceState
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * Search view model which will handle getting access token and searching for flights.
 */
open class SearchViewModel @Inject internal constructor(
        private val getFlightSchedules: GetFlightSchedules,
        private val getAccessToken: GetAccessToken
) : ViewModel() {
    private val schedulesLiveData: MutableLiveData<Resource<List<ScheduleEntity>>> = MutableLiveData()
    private val accessTokenLiveData: MutableLiveData<Resource<AccessTokenEntity>> = MutableLiveData()

    override fun onCleared() {
        getFlightSchedules.dispose()
        getAccessToken.dispose()
        super.onCleared()
    }

    fun getSchedulesLiveData(): LiveData<Resource<List<ScheduleEntity>>> {
        return schedulesLiveData
    }

    fun getAccessTokenLiveData(): LiveData<Resource<AccessTokenEntity>> {
        return accessTokenLiveData
    }

    fun fetchFlightSchedules(origin: String, destination: String, flightDate: String, limit: Int, offset: Int) {
        schedulesLiveData.postValue(Resource(ResourceState.LOADING, null, null, null))
        getFlightSchedules.execute(FlightSchedulesSubscriber(),
                GetFlightSchedules.Params.forGetFlightSchedules(
                        origin,
                        destination,
                        flightDate,
                        limit,
                        offset
                )
        )
    }

    fun authenticateApp(clientId: String, clientSecret: String, grantType: String) {
        accessTokenLiveData.postValue(Resource(ResourceState.LOADING, null, null, null))
        getAccessToken.execute(AccessTokenSubscriber(), GetAccessToken.Params.forGetAccessToken(
                clientId,
                clientSecret,
                grantType
        ))
    }

    inner class FlightSchedulesSubscriber : DisposableSubscriber<List<ScheduleEntity>>() {
        override fun onComplete() {}

        override fun onNext(schedules: List<ScheduleEntity>?) {
            schedulesLiveData.postValue(Resource(ResourceState.SUCCESS, schedules, null, null))
        }

        override fun onError(e: Throwable) {
            schedulesLiveData.postValue(Resource(ResourceState.ERROR, null, e.localizedMessage, e))
        }
    }

    inner class AccessTokenSubscriber : DisposableObserver<AccessTokenEntity>() {
        override fun onComplete() {}

        override fun onNext(token: AccessTokenEntity) {
            accessTokenLiveData.postValue(Resource(ResourceState.SUCCESS, token, null, null))
        }

        override fun onError(e: Throwable) {
            accessTokenLiveData.postValue(Resource(ResourceState.ERROR, null, e.message, e))
        }

    }
}