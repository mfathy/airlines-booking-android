package me.mfathy.airlinesbook.domain.interactor.token

import io.reactivex.Observable
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.domain.interactor.base.ObservableUseCase
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 18/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetAccessToken is a use case to get app access token.
 */
open class GetAccessToken @Inject constructor(
        private val airportsRepository: AirportsRepository,
        val subscriberThread: SubscribeThread,
        val postExecutionThread: ExecutionThread
) : ObservableUseCase<AccessTokenEntity, GetAccessToken.Params>(subscriberThread, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Params?): Observable<AccessTokenEntity> {
        if (params == null) throw IllegalArgumentException("Params can't be null!")
        return airportsRepository.getAccessToken(
                params.clientId,
                params.clientSecret,
                params.grantType

        ).toObservable()
    }

    data class Params constructor(val clientId: String, val clientSecret: String, val grantType: String) {
        companion object {
            fun forGetAccessToken(clientId: String, clientSecret: String, grantType: String): Params {
                return Params(clientId, clientSecret, grantType)
            }
        }
    }
}