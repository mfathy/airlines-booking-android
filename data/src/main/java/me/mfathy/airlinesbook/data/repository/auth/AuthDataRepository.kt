package me.mfathy.airlinesbook.data.repository.auth

import io.reactivex.Single
import me.mfathy.airlinesbook.data.config.AppConstants
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStoreFactory
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
        private val factory: AirportsDataStoreFactory
) : AuthRepository {
    override fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {

        //  Get cached access token.
        val memorySource = factory.getMemoryDataStore().get(AppConstants.KEY_SAVED_TOKEN)
        val remoteSource = getRemoteToken(clientId, clientSecret, grantType)

        return memorySource.flatMap {
            if (it.accessToken.isEmpty())
                remoteSource
            else
                Single.just(it)
        }
    }

    private fun getRemoteToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {
        return factory.getRemoteDataStore()
                .getAccessToken(
                        clientId,
                        clientSecret,
                        grantType
                ).flatMap {
                    //  Save cache expire trigger.
                    factory.getCacheDataStore().setLastCacheTime(it.expiresIn)
                            .andThen(Single.just(it))
                }.doOnSuccess {
                    //  Save access token after getting it from Remote Api.
                    factory.getMemoryDataStore().set(AppConstants.KEY_SAVED_TOKEN, it)
                }
    }
}