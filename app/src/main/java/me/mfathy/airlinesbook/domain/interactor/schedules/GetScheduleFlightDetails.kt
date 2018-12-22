package me.mfathy.airlinesbook.domain.interactor.schedules

import io.reactivex.Flowable
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.domain.interactor.base.FlowableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 20/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetScheduleFlightDetails use case to get all flight schedule airports information.
 */
open class GetScheduleFlightDetails @Inject constructor(
        private val dataRepository: AirportsRepository,
        val subscriberThread: SubscribeThread,
        val postExecutionThread: ExecutionThread
) : FlowableUseCase<List<AirportEntity>, GetScheduleFlightDetails.Params?>(
        subscriberThread, postExecutionThread
) {
    public override fun buildUseCaseObservable(params: Params?): Flowable<List<AirportEntity>> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return dataRepository.getFlightScheduleDetails(
                params.scheduleFlightDetails.toTypedArray(),
                params.lang,
                params.limit,
                params.offset
        )
    }

    data class Params constructor(val scheduleFlightDetails: List<String>,
                                  val lang: String, val limit: Int, val offset: Int) {
        companion object {
            fun forGetScheduleFlightDetails(scheduleFlightDetails: List<Pair<String, String>>,
                                            lang: String, limit: Int, offset: Int): Params {
                val airportCodes = scheduleFlightDetails.map { it.toList() }.flatten()
                return Params(airportCodes, lang, limit, offset)
            }
        }
    }

}