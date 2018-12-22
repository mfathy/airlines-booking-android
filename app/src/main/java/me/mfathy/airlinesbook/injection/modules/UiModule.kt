package me.mfathy.airlinesbook.injection.modules

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mfathy.airlinesbook.domain.executor.ExecutionThread
import me.mfathy.airlinesbook.domain.executor.SubscribeThread
import me.mfathy.airlinesbook.domain.executor.scheduler.IOThread
import me.mfathy.airlinesbook.domain.executor.scheduler.UIThread
import me.mfathy.airlinesbook.ui.details.FlightDetailsActivity
import me.mfathy.airlinesbook.ui.search.SearchFlightsActivity
import me.mfathy.airlinesbook.ui.select.SelectionActivity

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(executionThread: UIThread): ExecutionThread

    @Binds
    abstract fun bindSubscribeThread(executionThread: IOThread): SubscribeThread

    @ContributesAndroidInjector
    abstract fun contributesSearchFlightsActivity(): SearchFlightsActivity

    @ContributesAndroidInjector
    abstract fun contributesSelectionActivity(): SelectionActivity

    @ContributesAndroidInjector
    abstract fun contributesFlightDetailsActivity(): FlightDetailsActivity
}