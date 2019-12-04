package me.mfathy.airlinesbook.di.modules

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.repository.auth.AuthDataRepository
import me.mfathy.airlinesbook.data.repository.auth.AuthRepository
import javax.inject.Singleton

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */

@Module
class AuthModule {
    @Provides
    @Singleton
    @NonNull
    fun providesAuthRepository(repository: AuthDataRepository): AuthRepository = repository
}