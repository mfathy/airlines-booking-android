package me.mfathy.airlinesbook.data.mapper.cache

import me.mfathy.airlinesbook.data.mapper.EntityMapper


/**
 * Mapper contract to convert and map data entities.
 */
interface CacheEntityMapper<E, D>: me.mfathy.airlinesbook.data.mapper.EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

}