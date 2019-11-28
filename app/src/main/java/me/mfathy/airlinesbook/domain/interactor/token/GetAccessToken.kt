package me.mfathy.airlinesbook.domain.interactor.token

import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.repository.auth.AuthRepository
import me.mfathy.airlinesbook.domain.interactor.base.SingleUseCase
import me.mfathy.airlinesbook.extensions.rx.computation
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 18/12/2018.
 * dev.mfathy@gmail.com
 *
 * GetAccessToken is a use case to get app access token.
 */
open class GetAccessToken @Inject constructor(
        private val authRepository: AuthRepository
) : SingleUseCase<AccessTokenEntity, GetAccessToken.Params>() {
    override fun buildUseCaseObservable(params: Params): Single<AccessTokenEntity> {
        return authRepository.getAccessToken(
                params.clientId,
                params.clientSecret,
                params.grantType
        )

    }

    data class Params constructor(val clientId: String, val clientSecret: String, val grantType: String)
}