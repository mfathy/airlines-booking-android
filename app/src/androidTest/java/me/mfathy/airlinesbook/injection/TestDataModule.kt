package me.mfathy.airlinesbook.injection

import dagger.Module
import dagger.Provides
import me.mfathy.airlinesbook.data.repository.AirportsRepository
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): AirportsRepository {
        return mock(AirportsRepository::class.java)
    }

}