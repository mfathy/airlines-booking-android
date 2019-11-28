package me.mfathy.airlinesbook.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import me.mfathy.airlinesbook.AirlinesApplication
import me.mfathy.airlinesbook.injection.modules.*
import javax.inject.Singleton

/**
 * Dagger application components
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class,
    ApplicationModule::class,
    UiModule::class,
    ViewModelsModule::class,
    DataModule::class,
    CacheModule::class,
    MemoryModule::class,
    RemoteModule::class])
interface ApplicationComponent: AndroidInjector<AirlinesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

}