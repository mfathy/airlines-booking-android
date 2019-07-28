package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.interactor.base.FlowableUseCase
import me.mfathy.airlinesbook.extensions.rx.subscribeAndObserve
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 20/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetScheduleFlightDetails use case to get all flight schedule airports information.
 */
open class GetScheduleFlightDetails @Inject constructor(
        private val dataRepository: AirportsRepository) : FlowableUseCase<List<AirportEntity>, GetScheduleFlightDetails.Params>() {

    override fun buildUseCaseObservable(params: Params): Flowable<List<AirportEntity>> {
        return dataRepository.getFlightScheduleDetails(
                params.scheduleFlightDetails.toTypedArray(),
                params.lang,
                params.limit,
                params.offset
        ).subscribeAndObserve()
    }

    data class Params constructor(val scheduleFlightDetails: List<String>,
                                  val lang: String, val limit: Int, val offset: Int)
}