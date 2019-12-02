package me.mfathy.airlinesbook.di

import android.util.LruCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.mapper.memory.InMemoryTokenMapper
import me.mfathy.airlinesbook.data.store.memory.AuthMemoryStore
import me.mfathy.airlinesbook.data.store.memory.MemoryCache
import me.mfathy.airlinesbook.data.store.memory.models.InMemoryToken

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide cache dependencies.
 */
@Module
abstract class MemoryModule {
    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesLruCache(): LruCache<String, InMemoryToken> {
            return LruCache(1)
        }

        @Provides
        @JvmStatic
        fun providesInMemoryTokenMapper(): InMemoryTokenMapper = InMemoryTokenMapper()
    }


    @Binds
    abstract fun bindMemoryStore(memoryCache: AuthMemoryStore): MemoryCache
}