package com.sliidepoc.data.mapper

/**
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
interface DtoMapper<in Entity, out DomainDto> {
    fun mapToDtoModel(entity: Entity): DomainDto
}