package com.sliidepoc.data.http.api

import com.sliidepoc.data.http.models.UserResponse
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto

/**
 * Expose any work with Backend. Working with Retrofit, HTTP OK, etc ...
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
interface UsersProxy {

    suspend fun getUsersFromLastPage(): List<UserDto>

    suspend fun getUsers(page: Int = 1): List<UserDto>

    suspend fun addUser(name: String, email: String, gender: String, status: String): UserResponse?

    suspend fun deleteUser(userId: Int): Boolean
}
