package me.mfathy.airlinesbook.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import me.mfathy.airlinesbook.data.repository.airports.AirportsRepository
import me.mfathy.airlinesbook.injection.modules.UiModule
import me.mfathy.airlinesbook.injection.modules.ViewModelsModule
import me.mfathy.airlinesbook.test.TestApplication
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    TestApplicationModule::class,
    TestCacheModule::class,
    TestDataModule::class,
    ViewModelsModule::class,
    UiModule::class,
    TestRemoteModule::class])
interface TestApplicationComponent {

    fun airportsRepository(): AirportsRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)

}