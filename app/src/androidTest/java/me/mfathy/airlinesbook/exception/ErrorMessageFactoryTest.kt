package me.mfathy.airlinesbook.exception

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.exceptions.AuthException
import me.mfathy.airlinesbook.data.exceptions.NetworkConnectionException
import me.mfathy.airlinesbook.data.exceptions.NoAirportsException
import me.mfathy.airlinesbook.data.exceptions.NoFlightsException
import me.mfathy.airlinesbook.exceptions.ErrorMessageFactory
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Mohammed Fathy on 22/12/2018.
 * dev.mfathy@gmail.com
 *
 * Instrumentation test for ErrorMessageFactoryTest.
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class ErrorMessageFactoryTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun testNetworkConnectionErrorMessage() {
        val expectedMessage = context.resources.getString(R.string.exception_message_no_connection)
        val actualMessage = ErrorMessageFactory.create(context, me.mfathy.airlinesbook.data.exceptions.NetworkConnectionException())

        assertEquals(actualMessage, expectedMessage)
    }

    @Test
    fun testAuthExceptionErrorMessage() {
        val expectedMessage = context.resources.getString(R.string.exception_message_auth_error)
        val actualMessage = ErrorMessageFactory.create(context, me.mfathy.airlinesbook.data.exceptions.AuthException())

        assertEquals(actualMessage, expectedMessage)
    }

    @Test
    fun testNoAirportsExceptionErrorMessage() {
        val expectedMessage = context.resources.getString(R.string.exception_message_no_airports)
        val actualMessage = ErrorMessageFactory.create(context, me.mfathy.airlinesbook.data.exceptions.NoAirportsException())

        assertEquals(actualMessage, expectedMessage)
    }

    @Test
    fun testNoFlightsExceptionErrorMessage() {
        val expectedMessage = context.resources.getString(R.string.exception_message_no_flights)
        val actualMessage = ErrorMessageFactory.create(context, me.mfathy.airlinesbook.data.exceptions.NoFlightsException())

        assertEquals(actualMessage, expectedMessage)
    }

}