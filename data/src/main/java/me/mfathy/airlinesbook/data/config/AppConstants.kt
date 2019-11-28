package me.mfathy.airlinesbook.data.config

/**
 * Created by Mohammed Fathy on 18/12/2018.
 * dev.mfathy@gmail.com
 *
 * Application constants.
 */
object AppConstants {

    const val KEY_SAVED_TOKEN: String = "token"
    /**
     *  Base server url.
     */
    const val BASE_URL = "https://api.lufthansa.com/v1/"

    /**
     * Cache layer constants
     */
    const val QUERY_GET_CONFIG = "SELECT * FROM config WHERE id = 1"
    const val QUERY_GET_CACHED_AIRPORTS = "SELECT * FROM AIRPORTS"
    const val QUERY_GET_CACHED_AIRPORT = "SELECT * FROM AIRPORTS WHERE airport_code = :airportCode"
    const val QUERY_GET_CACHED_AIRPORTS_DETAILS = "SELECT * FROM AIRPORTS WHERE airport_code IN (:airportCodes)"
    const val QUERY_GET_CACHED_AIRPORTS_COUNT = "SELECT COUNT(*) FROM AIRPORTS"
    const val QUERY_GET_CACHED_AIRPORT_COUNT = "SELECT COUNT(*) FROM AIRPORTS WHERE airport_code =:airportCode"
    const val DELETE_CACHED_AIRPORT = "DELETE FROM AIRPORTS"

    const val APP_DATABASE_NAME = "airports.db"
}