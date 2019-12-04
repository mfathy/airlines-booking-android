package me.mfathy.airlinesbook.injection.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import me.mfathy.airlinesbook.AirlinesApplication
import me.mfathy.airlinesbook.di.modules.*
import me.mfathy.airlinesbook.injection.modules.ApplicationModule
import me.mfathy.airlinesbook.injection.modules.UiModule
import me.mfathy.airlinesbook.injection.modules.ViewModelsModule
import javax.inject.Singleton

/**
 * Dagger application components
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ApplicationModule::class,
    UiModule::class,
    ViewModelsModule::class,
    AuthModule::class,
    AirportsModule::class,
    SchedulesModule::class,
    CacheModule::class,
    MemoryModule::class,
    RemoteModule::class])
interface ApplicationComponent : AndroidInjector<AirlinesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}