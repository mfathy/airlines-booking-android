package me.mfathy.airlinesbook.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.preference.PreferenceHelper
import me.mfathy.airlinesbook.data.preference.PreferenceHelperImpl
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.local.AirportsCache
import me.mfathy.airlinesbook.data.store.local.AirportsCacheDataStore
import me.mfathy.airlinesbook.data.store.local.db.AirportsDatabase
import javax.inject.Singleton

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide cache dependencies.
 */
@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): AirportsDatabase {
            return AirportsDatabase.getInstance(application)
        }

        @Provides
        @JvmStatic
        fun providesPreferenceHelper(application: Application): PreferenceHelper {
            return PreferenceHelperImpl(application)
        }
    }


    @Binds
    abstract fun bindCacheStore(cache: AirportsCacheDataStore): AirportsCache
}