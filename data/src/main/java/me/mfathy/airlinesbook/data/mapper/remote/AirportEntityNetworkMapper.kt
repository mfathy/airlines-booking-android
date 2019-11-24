package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.mapper.EntityMapper
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.remote.model.Airport
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 *
 * A helper class to map AirportEntity to/from Airport
 */
open class AirportEntityNetworkMapper @Inject constructor() : RemoteEntityMapper<AirportEntity, Airport> {
    override fun mapToEntity(domain: Airport): AirportEntity {
        return AirportEntity(
                domain.names.name.title,
                domain.airportCode,
                domain.position.coordinate.latitude,
                domain.position.coordinate.longitude,
                domain.cityCode,
                domain.countryCode,
                domain.locationType
        )
    }

}