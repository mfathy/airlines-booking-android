package me.mfathy.airlinesbook.domain.interactor.airports

import io.reactivex.Completable
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.interactor.base.CompletableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * ClearAirports use case used for clearing data stores from airports.
 */
open class ClearAirports @Inject constructor(
        private val dataRepository: AirportsRepository)
    : CompletableUseCase() {

    override fun buildUseCaseCompletable(): Completable {
        return dataRepository.clearAirports()
    }

    class Params
}