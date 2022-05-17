package com.sliidepoc.domain.api.data

import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
interface UsersRepository {

    suspend fun getUsersFromLastPage(): List<UserDto>

    suspend fun getUsers(page: Int): List<UserDto>

    suspend fun addUser(name: String, email: String, gender: String, status: String): Boolean

    suspend fun deleteUser(userId: Int): Boolean

    fun countSavedUsers(): Flow<Int>
}
