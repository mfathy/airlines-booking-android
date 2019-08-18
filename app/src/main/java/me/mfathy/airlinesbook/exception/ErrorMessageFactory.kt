package me.mfathy.airlinesbook.exception

import android.content.Context
import com.google.gson.Gson
import me.mfathy.airlinesbook.R
import me.mfathy.airlinesbook.data.store.remote.model.ErrorResponse
import retrofit2.HttpException

/**
 * Created by Mohammed Fathy on 31/08/2018.
 * dev.mfathy@gmail.com
 *
 * Factory used to create error messages from an Exception as a condition.
 */
object ErrorMessageFactory {

    /**
     * Creates a String representing an error message.
     *
     * @param context Android context.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return [String] an error message.
     */
    fun create(context: Context, exception: Throwable): String {
        var message = context.resources.getString(R.string.exception_message_generic)

        when (exception) {
            is NetworkConnectionException -> message = context.resources.getString(R.string.exception_message_no_connection)
            is AuthException -> message = context.resources.getString(R.string.exception_message_auth_error)
            is NoAirportsException -> message = context.resources.getString(R.string.exception_message_no_airports)
            is NoFlightsException -> message = context.resources.getString(R.string.exception_message_no_flights)
            is HttpException -> {
                if (exception.code() == 404) {
                    message = context.resources.getString(R.string.exception_message_no_schedules)
                } else {
                    exception.response()?.errorBody()?.string()?.let {
                        //  Just in case parsing goes wrong or any other exception.
                        message = try {
                            val errorResponse = Gson().fromJson(it, ErrorResponse::class.java)
                            if (errorResponse.processingErrors.processingError.description.contains("."))
                                errorResponse.processingErrors.processingError.description.split(".").first()
                            else
                                errorResponse.processingErrors.processingError.description
                        } catch (ex: Exception) {
                            context.resources.getString(R.string.exception_message_generic)
                        }

                    }
                }
            }
        }

        return message
    }
}
