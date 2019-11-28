package me.mfathy.airlinesbook.data.mapper.memory

import me.mfathy.airlinesbook.data.mapper.EntityMapper


/**
 * Mapper contract to convert and map data entities.
 */
interface MemoryEntityMapper<E, D>: EntityMapper<E, D> {

    fun mapFromEntity(entity: E): D

}