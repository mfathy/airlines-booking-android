package me.mfathy.airlinesbook.domain.interactor.token

import io.reactivex.Observable
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.interactor.base.ObservableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 18/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetAccessToken is a use case to get app access token.
 */
open class GetAccessToken @Inject constructor(
        private val airportsRepository: AirportsRepository
) : ObservableUseCase<AccessTokenEntity, GetAccessToken.Params>() {
    override fun buildUseCaseObservable(params: Params): Observable<AccessTokenEntity> {
        return airportsRepository.getAccessToken(
                params.clientId,
                params.clientSecret,
                params.grantType

        ).toObservable()
    }

    data class Params constructor(val clientId: String, val clientSecret: String, val grantType: String)
}