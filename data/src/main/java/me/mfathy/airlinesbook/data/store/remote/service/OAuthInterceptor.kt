package me.mfathy.airlinesbook.data.store.remote.service

import android.annotation.SuppressLint
import me.mfathy.airlinesbook.data.store.remote.model.RequestHeaders
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
        private val headers: RequestHeaders
) : Interceptor {

    @SuppressLint("DefaultLocale")
    override fun intercept(chain: Interceptor.Chain): Response {

        val original = chain.request()
        return if (original.url.encodedPath.contains("oauth"))
            chain.proceed(original)
        else {
            val builder = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "${headers.accessToken.tokenType.capitalize()} ${headers.accessToken.accessToken}")

            val request = builder.build()
            chain.proceed(request)
        }

    }

}