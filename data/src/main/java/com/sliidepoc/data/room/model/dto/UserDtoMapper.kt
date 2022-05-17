package com.sliidepoc.data.room.model.dto

import com.sliidepoc.data.room.model.UserImpl
import com.sliidepoc.data.mapper.DtoMapper
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
/**
 * POC for DTO mapping from entity to DTO object required on other layer like: UI.
 */
@Singleton
class UserDtoMapper @Inject constructor() : DtoMapper<UserImpl, UserDto> {
    override fun mapToDtoModel(entity: UserImpl): UserDto {
        return UserDto(
            entity.extId,
            entity.name,
            entity.email,
            entity.creationDateTime
        )
    }
}
