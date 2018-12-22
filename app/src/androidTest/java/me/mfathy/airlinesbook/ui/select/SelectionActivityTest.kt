package me.mfathy.airlinesbook.ui.select


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.factory.AirportTestFactory
import me.mfathy.airlinesbook.test.TestApplication
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Espresso ui test to ensure that SelectionActivityTest is working right.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class SelectionActivityTest {

    @Rule
    @JvmField
    val mActivityTestRule = ActivityTestRule<SelectionActivity>(SelectionActivity::class.java, false, false)

    private val entity = AirportTestFactory.makeAirportEntity()

    @Before
    fun setUp() {
        stubAirportsRepositoryGetAirports(Observable.just(listOf(entity)))
    }

    private fun stubAirportsRepositoryGetAirports(flowable: Observable<List<AirportEntity>>?) {
        whenever(TestApplication.appComponent().airportsRepository().getAirports(any(), any(), any()))
                .thenReturn(flowable)
    }

    @Test
    fun selectActivityTest() {
        mActivityTestRule.launchActivity(null)

        Thread.sleep(3000)

        val textView = onView(
                allOf(withId(R.id.textViewAirportCode), withText(entity.airportCode), isDisplayed()))
        textView.check(matches(withText(entity.airportCode)))

        val textView2 = onView(
                allOf(withId(R.id.textViewCityName), withText(entity.name), isDisplayed()))
        textView2.check(matches(withText(entity.name)))
    }
}
