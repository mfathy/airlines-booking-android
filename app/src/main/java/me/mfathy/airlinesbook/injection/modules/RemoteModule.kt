package me.mfathy.airlinesbook.injection.modules

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.BuildConfig
import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.remote.AirportsRemote
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import me.mfathy.airlinesbook.data.store.remote.service.AuthServiceApi
import me.mfathy.airlinesbook.data.store.remote.service.OAuthInterceptor
import me.mfathy.airlinesbook.data.store.remote.service.RemoteServiceApi
import me.mfathy.airlinesbook.data.store.remote.service.ServiceFactory
import me.mfathy.airlinesbook.data.store.remote.utils.NetworkUtils
import me.mfathy.airlinesbook.data.store.remote.utils.NetworkUtilsImpl

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideRemoteService(oAuthInterceptor: OAuthInterceptor): RemoteServiceApi {
            return ServiceFactory.makeRemoteService(BuildConfig.DEBUG, oAuthInterceptor)
        }

        @Provides
        @JvmStatic
        fun provideAuthService(): AuthServiceApi {
            return ServiceFactory.makeAuthService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun provideOAuthInterceptor(preferenceHelper: PreferenceHelper): OAuthInterceptor {
            return OAuthInterceptor(preferenceHelper)
        }

        @Provides
        @JvmStatic
        fun providesNetworkUtils(app: Application): NetworkUtils {
            return NetworkUtilsImpl(app)
        }

    }

    @Binds
    abstract fun bindRemoteStore(remote: AirportsRemoteDataStore): AirportsRemote

}