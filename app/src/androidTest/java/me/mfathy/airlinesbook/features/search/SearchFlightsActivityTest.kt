package me.mfathy.airlinesbook.features.search


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.test.TestApplication
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

/**
 * Espresso ui test to ensure that SearchFlightsActivity is working right.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class SearchFlightsActivityTest {


    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule<SearchFlightsActivity>(SearchFlightsActivity::class.java, false, false)

    private val token = AirportTestFactory.makeAccessTokenEntity()
    private val schedule01 = AirportTestFactory.makeScheduleEntity()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
        stubAirportsRepositoryGetAccessToken(Single.just(token))
        stubAirportsRepositorySearchResult(Flowable.just(listOf(schedule01)))
    }

    @Test
    fun searchFlightsActivityTest() {
        mActivityTestRule.launchActivity(null)

        val textView = onView(
                allOf(withId(R.id.originAirportCode), withText("AAR"), isDisplayed()))
        textView.check(matches(withText("AAR")))

        val textView2 = onView(
                allOf(withId(R.id.originAirportName), withText("Aarhus"), isDisplayed()))
        textView2.check(matches(withText("Aarhus")))

        val textView3 = onView(
                allOf(withId(R.id.destinationAirportCode), withText("ABV"), isDisplayed()))
        textView3.check(matches(withText("ABV")))

        val textView4 = onView(
                allOf(withId(R.id.destinationAirportName), withText("Abuja International"), isDisplayed()))
        textView4.check(matches(withText("Abuja International")))

        val appCompatButton = onView(
                allOf(withId(R.id.buttonSearchFlights), withText("Search Flights"), isDisplayed()))
        appCompatButton.perform(click())

        val textView5 = onView(
                allOf(withId(R.id.textViewDuration), withText(schedule01.duration), isDisplayed()))
        textView5.check(matches(withText(schedule01.duration)))

        schedule01.flights?.count()?.let {
            val stopsString = context.resources.getQuantityString(R.plurals.stops_plurals, it, it)
            val textView6 = onView(
                    allOf(withId(R.id.textViewStops), isDisplayed()))
            textView6.check(matches(withText(stopsString)))
        }
    }

    private fun stubAirportsRepositoryGetAccessToken(observable: Single<AccessTokenEntity>) {
        Mockito.`when`(TestApplication.appComponent().airportsRepository().getAccessToken(me.mfathy.test.tools.any(), me.mfathy.test.tools.any(), me.mfathy.test.tools.any()))
                .thenReturn(observable)
    }

    private fun stubAirportsRepositorySearchResult(observable: Flowable<List<ScheduleEntity>>) {
        Mockito.`when`(TestApplication.appComponent().airportsRepository()
                .getFlightSchedules(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(observable)
    }
}
