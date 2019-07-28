package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.interactor.base.FlowableUseCase
import me.mfathy.airlinesbook.extensions.rx.subscribeAndObserve
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetFlightSchedules use case to get all flight schedule information from the data layer.
 */

open class GetFlightSchedules @Inject constructor(
        private val dataRepository: AirportsRepository)
    : FlowableUseCase<List<ScheduleEntity>, GetFlightSchedules.Params>() {
    override fun buildUseCaseObservable(params: Params): Flowable<List<ScheduleEntity>> {
        return dataRepository.getFlightSchedules(params.origin,
                params.destination,
                params.flightDate,
                params.limit,
                params.offset).subscribeAndObserve()
    }

    data class Params constructor(val origin: String,
                                  val destination: String,
                                  val flightDate: String,
                                  val limit: Int,
                                  val offset: Int)
}
