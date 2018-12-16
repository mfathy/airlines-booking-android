package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.BuildConfig
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.remote.AirportsRemoteDataStore
import me.mfathy.airlinesbook.data.store.remote.service.RemoteService
import me.mfathy.airlinesbook.data.store.remote.service.RemoteServiceFactory

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
        fun provideRemoteService(): RemoteService {
            return RemoteServiceFactory.makeRemoteService(BuildConfig.DEBUG)
        }
    }

    @Binds
    abstract fun bindRemoteStore(cache: AirportsRemoteDataStore): AirportsDataStore

}