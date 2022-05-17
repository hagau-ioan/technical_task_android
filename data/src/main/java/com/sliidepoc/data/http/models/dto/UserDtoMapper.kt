package com.sliidepoc.data.http.models.dto

import com.sliidepoc.data.http.models.UserResponse
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
class UserDtoMapper @Inject constructor() : DtoMapper<UserResponse, UserDto> {
    override fun mapToDtoModel(entity: UserResponse): UserDto {
        return UserDto(
            entity.id.toInt(),
            entity.name,
            entity.email,
            null
        )
    }
}