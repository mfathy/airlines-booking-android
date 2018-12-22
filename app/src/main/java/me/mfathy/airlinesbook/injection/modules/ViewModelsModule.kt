package me.mfathy.airlinesbook.injection.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import me.mfathy.airlinesbook.injection.ViewModelFactory
import me.mfathy.airlinesbook.ui.details.FlightDetailsViewModel
import me.mfathy.airlinesbook.ui.search.SearchViewModel
import me.mfathy.airlinesbook.ui.select.SelectionViewModel
import kotlin.reflect.KClass

/**
 * Dagger module to provide ViewModel dependencies.
 */
@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectionViewModel::class)
    abstract fun bindSelectionViewModel(viewModel: SelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FlightDetailsViewModel::class)
    abstract fun bindFlightDetailsViewModel(viewModel: FlightDetailsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
