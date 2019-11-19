package me.mfathy.airlinesbook.domain.interactor.token

import io.reactivex.Observable
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.AuthRepository
import me.mfathy.airlinesbook.domain.interactor.base.ObservableUseCase
import me.mfathy.airlinesbook.extensions.rx.subscribeAndObserve
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 18/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetAccessToken is a use case to get app access token.
 */
open class GetAccessToken @Inject constructor(
        private val authRepository: AuthRepository
) : ObservableUseCase<AccessTokenEntity, GetAccessToken.Params>() {
    override fun buildUseCaseObservable(params: Params): Observable<AccessTokenEntity> {
        return authRepository.getAccessToken(
                params.clientId,
                params.clientSecret,
                params.grantType

        ).toObservable().subscribeAndObserve()
    }

    data class Params constructor(val clientId: String, val clientSecret: String, val grantType: String)
}