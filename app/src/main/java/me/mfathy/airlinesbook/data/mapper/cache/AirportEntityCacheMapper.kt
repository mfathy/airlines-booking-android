package me.mfathy.airlinesbook.data.mapper.cache

import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.local.models.CachedAirport
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * A helper class to map AirportEntity to/from CachedAirport
 */
class AirportEntityCacheMapper @Inject constructor() : CacheEntityMapper<AirportEntity, CachedAirport> {

    override fun mapFromEntity(entity: AirportEntity): CachedAirport {
        return CachedAirport(
                entity.name,
                entity.airportCode,
                entity.latitude,
                entity.longitude,
                entity.cityCode,
                entity.countryCode,
                entity.locationType
        )
    }

    override fun mapToEntity(domain: CachedAirport): AirportEntity {
        return AirportEntity(
                domain.name,
                domain.airportCode,
                domain.latitude,
                domain.longitude,
                domain.cityCode,
                domain.countryCode,
                domain.locationType
        )
    }
}