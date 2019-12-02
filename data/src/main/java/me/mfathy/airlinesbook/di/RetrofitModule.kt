package me.mfathy.airlinesbook.di

import android.app.Application
import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.BuildConfig
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.store.remote.model.RequestHeaders
import me.mfathy.airlinesbook.data.store.remote.service.AuthServiceApi
import me.mfathy.airlinesbook.data.store.remote.service.OAuthInterceptor
import me.mfathy.airlinesbook.data.store.remote.service.RemoteServiceApi
import me.mfathy.airlinesbook.data.store.remote.service.ServiceFactory
import me.mfathy.airlinesbook.data.store.remote.utils.NetworkUtils
import me.mfathy.airlinesbook.data.store.remote.utils.NetworkUtilsImpl
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Provides
    @Singleton
    @NonNull
    fun provideAuthService(): AuthServiceApi {
        return ServiceFactory.provideAuthService(BuildConfig.DEBUG)
    }

    @Provides
    @Singleton
    @NonNull
    fun provideRemoteService(oAuthInterceptor: OAuthInterceptor): RemoteServiceApi {
        return ServiceFactory.provideRemoteService(BuildConfig.DEBUG, oAuthInterceptor)
    }

    @Provides
    @Singleton
    @NonNull
    fun provideRequestHeaders(): RequestHeaders {
        val headers = RequestHeaders()
        headers.accessToken = AccessTokenEntity()
        return RequestHeaders()
    }

    @Provides
    @Singleton
    @NonNull
    fun provideOAuthInterceptor(@NonNull headers: RequestHeaders): OAuthInterceptor {
        return OAuthInterceptor(headers)
    }

    @Provides
    @Singleton
    @NonNull
    fun providesNetworkUtils(app: Application): NetworkUtils {
        return NetworkUtilsImpl(app)
    }
}