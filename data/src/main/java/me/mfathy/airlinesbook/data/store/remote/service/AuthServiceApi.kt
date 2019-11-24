package me.mfathy.airlinesbook.data.store.remote.service

import io.reactivex.Single
import me.mfathy.airlinesbook.data.store.remote.model.AccessToken
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by Mohammed Fathy on 07/12/2018.
 * dev.mfathy@gmail.com
 *
 * AuthServiceApi retrofit end point to access remote server api to get access token to be used in all other web services.
 */
interface AuthServiceApi {

    @FormUrlEncoded
    @POST("oauth/token")
    fun getAccessToken(@Field("client_id") clientId: String,
                       @Field("client_secret") clientSecret: String,
                       @Field("grant_type") grantType: String): Single<AccessToken>

}