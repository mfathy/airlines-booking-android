package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.mapper.EntityMapper
import me.mfathy.airlinesbook.data.model.AirportEntity
import me.mfathy.airlinesbook.data.store.remote.model.Airport
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**
 * Created by Mohammed Fathy on 15/12/2018.
 * dev.mfathy@gmail.com
 */
open class AirportEntityNetworkMapper @Inject constructor(): EntityMapper<AirportEntity, Airport> {
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

    override fun mapFromEntity(entity: AirportEntity): Airport {
        throw UnsupportedOperationException("Airport is readonly: parsed from remote data store only.")
    }
}