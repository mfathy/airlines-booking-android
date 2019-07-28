package me.mfathy.airlinesbook.ui.select

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.processors.PublishProcessor
import io.reactivex.subscribers.DisposableSubscriber
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.domain.interactor.airports.GetAirports
import me.mfathy.airlinesbook.extensions.rx.observeMainThread
import me.mfathy.airlinesbook.extensions.rx.subscribeAndObserve
import me.mfathy.airlinesbook.ui.base.BaseViewModel
import me.mfathy.airlinesbook.ui.state.Resource
import me.mfathy.airlinesbook.ui.state.ResourceState
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 17/12/2018.
 * dev.mfathy@gmail.com
 *
 * SelectionViewModel which handles get all airports.
 */
open class SelectionViewModel @Inject internal constructor(
        private val getAirports: GetAirports
) : BaseViewModel() {

    private val airportsLiveData: MutableLiveData<Resource<List<AirportEntity>>> = MutableLiveData()

    /**
     * PublishProcessor used for pagination, it will emit next/previous page.
     */
    private var pagination = PublishProcessor.create<Pair<Int, String>>()

    fun getPaginator() = pagination

    fun startPagination() {
        subscribeToPagination(AirportsSubscriber())
    }

    fun subscribeToPagination(airportsSubscriber: AirportsSubscriber) {

        val airportsPage = pagination
                .onBackpressureDrop()
                .doOnNext { page ->
                    showProgress(page.first)
                }
                .concatMap { page ->

                    val params = GetAirports.Params(
                            page.second,
                            100,
                            page.first
                    )
                    getAirports.execute(params)
                            .subscribeAndObserve()

                }
                .observeMainThread()
                .subscribeWith(airportsSubscriber)

        addDisposables(airportsPage)
    }

    private fun showProgress(page: Int?) {
        page?.let {
            if (it == 0) airportsLiveData.postValue(Resource(ResourceState.LOADING, null, null, null))
        }
    }

    fun getAirportsLiveData(): LiveData<Resource<List<AirportEntity>>> {
        return airportsLiveData
    }

//    fun fetchAirports(lang: String, limit: Int, offset: Int) {
//        airportsLiveData.postValue(Resource(ResourceState.LOADING, null, null, null))
//        val params = GetAirports.Params(
//                lang,
//                limit,
//                offset
//        )
//        val airportsSubscriber = getAirports.execute(params, AirportsSubscriber())
//        addDisposables(airportsSubscriber)
//
//    }

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