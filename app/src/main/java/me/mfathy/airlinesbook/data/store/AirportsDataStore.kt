package me.mfathy.airlinesbook.data.store

import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 */
interface AirportsDataStore {

    /**
     * Returns a flowable which emits a list of airport entities.
     * @param lang the language the user would like to receive his response in.
     * @param limit the number of airports >> this should be from 1 to 100.
     * @param offset the paging number.
     * @return a flowable which emits a list of airport entities or error.
     */
    fun getAirports(lang: String, limit: Int, offset: Int): Flowable<List<AirportEntity>>

    /**
     * Returns a Single which emits an airport entity
     * @param airportCode the airport we need it's details.
     * @param lang the language the user would like to receive his response in.
     * @param limit the number of airports >> this should be from 1 to 100.
     * @param offset the paging number.
     * @return a flowable which emits a list of airport entities or error.
     */
    fun getAirport(airportCode: String, lang: String, limit: Int, offset: Int): Single<AirportEntity>

    /**
     * Returns a flowable which emits a list of flight schedule entities.
     * @param origin airport code that the user will travel from
     * @param destination airport code that the user will travel to
     * @param limit the number of flight schedules.
     * @return a flowable which emits a list of flight schedule entities or error.
     */
    fun getFlightSchedules(origin: String,
                           destination: String,
                           flightDate: String,
                           limit: Int,
                           offset: Int): Flowable<List<ScheduleEntity>>
}