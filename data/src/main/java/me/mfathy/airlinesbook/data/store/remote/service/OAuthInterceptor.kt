package me.mfathy.airlinesbook.data.store.remote.service

import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Authentication interceptor to provide access token to all remote api services.
 */
class OAuthInterceptor @Inject constructor(
        private val preferenceHelper: PreferenceHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val accessToken = preferenceHelper.getAccessToken()

        val original = chain.request()
        val builder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "${accessToken.tokenType.capitalize()} ${accessToken.accessToken}")

        val request = builder.build()
        return chain.proceed(request)
    }

}