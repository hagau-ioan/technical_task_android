package com.sliidepoc.domain.usecases.http

import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
@Singleton
class GetUsersFromLastPageUseCase @Inject constructor(private val repository: UsersRepository) {
    operator fun invoke(): Flow<List<UserDto>> = flow { emit(repository.getUsersFromLastPage()) }
}
