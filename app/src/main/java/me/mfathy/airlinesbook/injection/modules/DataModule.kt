package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import me.mfathy.airlinesbook.data.repository.airports.AirportsDataRepository
import me.mfathy.airlinesbook.data.repository.airports.AirportsRepository
import me.mfathy.airlinesbook.data.repository.auth.AuthDataRepository
import me.mfathy.airlinesbook.data.repository.auth.AuthRepository
import me.mfathy.airlinesbook.data.repository.schedules.SchedulesDataRepository
import me.mfathy.airlinesbook.data.repository.schedules.SchedulesRepository

/**
 * Dagger module to provide data repository dependencies.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindAuthRepository(repository: AuthDataRepository): AuthRepository

    @Binds
    abstract fun bindAirportsRepository(repository: AirportsDataRepository): AirportsRepository

    @Binds
    abstract fun bindSchedulesRepository(repository: SchedulesDataRepository): SchedulesRepository

}