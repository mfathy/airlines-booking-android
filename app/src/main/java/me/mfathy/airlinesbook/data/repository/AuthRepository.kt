package me.mfathy.airlinesbook.data.repository

import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AccessTokenEntity

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * Data repository contact for authentication.
 */
interface AuthRepository  {

    /**
     * Returns an access token to be used in accessing the rest of remote API from the server.
     * @param clientId lufthansa application key.
     * @param clientSecret lufthansa application secret key.
     * @param grantType lufthansa grant access type
     * @return a Single observable which will emit an AccessTokenEntity or error.
     */
    fun getAccessToken(clientId: String,
                       clientSecret: String,
                       grantType: String): Single<AccessTokenEntity>

}