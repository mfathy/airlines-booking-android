package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.domain.interactor.base.FlowableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetFlightSchedules use case to get all flight schedule information from the data layer.
 */

open class GetFlightSchedules @Inject constructor(
        private val dataRepository: AirportsRepository,
        val subscriberThread: SubscribeThread,
        val postExecutionThread: ExecutionThread)
    : FlowableUseCase<List<ScheduleEntity>, GetFlightSchedules.Params?>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseObservable(params: GetFlightSchedules.Params?): Flowable<List<ScheduleEntity>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return dataRepository.getFlightSchedules(params.origin,
                params.destination,
                params.flightDate,
                params.limit,
                params.offset)
    }

    data class Params constructor(val origin: String,
                                  val destination: String,
                                  val flightDate: String,
                                  val limit: Int,
                                  val offset: Int) {
        companion object {
            fun forGetFlightSchedules(origin: String, destination: String, flightDate: String, limit: Int, offset: Int): Params {
                return Params(origin, destination, flightDate, limit, offset)
            }
        }
    }
}
