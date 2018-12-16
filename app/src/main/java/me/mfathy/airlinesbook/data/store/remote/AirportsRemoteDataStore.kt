package me.mfathy.airlinesbook.data.store.remote

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.mfathy.airlinesbook.data.mapper.remote.AccessTokenEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.AirportEntityNetworkMapper
import me.mfathy.airlinesbook.data.mapper.remote.ScheduleEntityNetworkMapper
import me.mfathy.airlinesbook.data.model.AccessTokenEntity
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.model.ScheduleEntity
import me.mfathy.airlinesbook.data.store.AirportsDataStore
import me.mfathy.airlinesbook.data.store.remote.service.RemoteService
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
open class AirportsRemoteDataStore @Inject constructor(
        private val service: RemoteService,
        private val accessDataMapper: AccessTokenEntityNetworkMapper,
        private val airportsDataMapper: AirportEntityNetworkMapper,
        private val scheduleDataMapper: ScheduleEntityNetworkMapper
) : AirportsDataStore {
    override fun getAccessToken(clientId: String, clientSecret: String, grantType: String): Single<AccessTokenEntity> {
        return service.getAccessToken(clientId, clientSecret, grantType).map {
            val accessToken = it.copy(clientId = clientId)
            accessDataMapper.mapToEntity(accessToken)
        }
    }

    override fun getAirports(lang: String, limit: Int, offset: Int): Flowable<List<AirportEntity>> {
        return service.getAirports(lang, limit, offset).map { airportsResponse ->
            airportsResponse.airportResource.airports.airportList.map {
                airportsDataMapper.mapToEntity(it)
            }
        }.toFlowable()
    }

    override fun getFlightSchedules(origin: String, destination: String, limit: Int, offset: Int): Flowable<List<ScheduleEntity>> {
        return service.getFlightSchedules(origin, destination, limit, offset).map { scheduleResponse ->
            scheduleResponse.scheduleResource.schedule.map {
                scheduleDataMapper.mapToEntity(it)
            }
        }.toFlowable()
    }

    override fun saveAirports(airportEntities: List<AirportEntity>): Completable {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun clearAirports(): Completable {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun saveAccessToken(accessTokenEntity: AccessTokenEntity): Completable {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun clearAccessToken(): Completable {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun areAirportsCached(): Single<Boolean> {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun isAccessTokenCached(): Single<Boolean> {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun isCacheExpired(): Single<Boolean> {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        throw UnsupportedOperationException("Not supported in RemoteDataStore")
    }
}