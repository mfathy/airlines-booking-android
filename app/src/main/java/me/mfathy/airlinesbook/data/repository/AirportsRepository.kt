package me.mfathy.airlinesbook.data.repository

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * Data repository contact to define layer actions.
 */
interface AirportsRepository {

    /**
     * Returns a flowable which emits a list of airport entities.
     * @param lang the language the user would like to receive his response in.
     * @param limit the number of airports >> this should be from 1 to 100.
     * @param offset the paging number.
     * @return an observable which emits a list of airport entities or error.
     */
    fun getAirports(lang: String, limit: Int, offset: Int): Observable<List<AirportEntity>>

    /**
     * Returns a flowable which emits a list of flight schedule entities.
     * @param origin airport code that the user will travel from
     * @param destination airport code that the user will travel to
     * @param limit the number of flight schedules.
     * @return a flowable which emits a list of flight schedule entities or error.
     */
    fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>>

    /**
     * Clears all AirportEntity from the local data store.
     * @return Completable observable indicates success of failure.
     */
    fun clearAirports(): Completable

}