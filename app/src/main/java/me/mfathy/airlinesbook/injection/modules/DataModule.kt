package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.data.repository.AirportsRepositoryImpl

/**
 * Dagger module to provide data repository dependencies.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataRepository: AirportsRepositoryImpl): AirportsRepository

}