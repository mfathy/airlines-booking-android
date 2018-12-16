package me.mfathy.airlinesbook.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import me.mfathy.airlinesbook.injection.ViewModelFactory
import kotlin.reflect.KClass

/**
 * Dagger module to provide ViewModel dependencies.
 */
@Module
abstract class ViewModelsModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
