package me.mfathy.airlinesbook.injection.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mfathy.airlinesbook.features.details.FlightDetailsActivity
import me.mfathy.airlinesbook.features.search.SearchFlightsActivity
import me.mfathy.airlinesbook.features.select.SelectionActivity

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class UiModule {


    @ContributesAndroidInjector
    abstract fun contributesSearchFlightsActivity(): SearchFlightsActivity

    @ContributesAndroidInjector
    abstract fun contributesSelectionActivity(): SelectionActivity

    @ContributesAndroidInjector
    abstract fun contributesFlightDetailsActivity(): FlightDetailsActivity
}