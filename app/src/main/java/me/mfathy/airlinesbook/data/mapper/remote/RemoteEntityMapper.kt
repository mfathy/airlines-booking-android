package me.mfathy.airlinesbook.data.mapper.remote

import me.mfathy.airlinesbook.data.mapper.EntityMapper


/**
 * Mapper contract to convert and map data entities.
 */
interface RemoteEntityMapper<E, D> : EntityMapper<E, D> {
    override fun mapToEntity(domain: D): E
}