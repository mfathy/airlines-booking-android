package me.mfathy.airlinesbook.data.mapper


/**
 * Mapper contract to convert and map data entities.
 */
interface EntityMapper<E, D> {

    fun mapToEntity(domain: D): E

}