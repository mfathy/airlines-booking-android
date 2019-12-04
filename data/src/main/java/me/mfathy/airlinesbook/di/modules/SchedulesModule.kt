package me.mfathy.airlinesbook.di.modules

import androidx.annotation.NonNull
import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.repository.schedules.SchedulesDataRepository
import me.mfathy.airlinesbook.data.repository.schedules.SchedulesRepository
import javax.inject.Singleton

/**
 * Created by Mohammed Fathy on 08/12/2018.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */

@Module
class SchedulesModule {
    @Provides
    @Singleton
    @NonNull
    fun providesSchedulesRepository(repository: SchedulesDataRepository): SchedulesRepository = repository
}