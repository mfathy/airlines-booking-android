package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import me.mfathy.airlinesbook.data.repository.AirportsDataRepository

/**
 * Dagger module to provide data repository dependencies.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindDataRepository(dataDataRepository: AirportsDataRepository): AirportsRepository

}