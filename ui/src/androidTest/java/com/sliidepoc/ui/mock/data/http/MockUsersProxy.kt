package com.sliidepoc.ui.mock.data.http

import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.data.http.models.UserResponse
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import javax.inject.Inject

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockUsersProxy @Inject constructor(): UsersProxy {
    override suspend fun getUsersFromLastPage(): List<UserDto> {
        return listOf(
            UserDto(1, "Name 1", "email_1@gmail.com", System.currentTimeMillis().toString()),
            UserDto(2, "Name 2", "email_2@gmail.com", System.currentTimeMillis().toString()),
            UserDto(3, "Name 3", "email_3@gmail.com", System.currentTimeMillis().toString()),
            UserDto(4, "Name 4", "email_4@gmail.com", System.currentTimeMillis().toString()),
            UserDto(5, "Name 5", "email_5@gmail.com", System.currentTimeMillis().toString()),
            UserDto(6, "Name 6", "email_6@gmail.com", System.currentTimeMillis().toString()),
            UserDto(7, "Name 7", "email_7@gmail.com", System.currentTimeMillis().toString()),
            UserDto(8, "Name 8", "email_8@gmail.com", System.currentTimeMillis().toString())
        )
    }

    override suspend fun getUsers(page: Int): List<UserDto> {
        return listOf(
            UserDto(1, "Name 1", "email_1@gmail.com", System.currentTimeMillis().toString()),
            UserDto(2, "Name 2", "email_2@gmail.com", System.currentTimeMillis().toString()),
            UserDto(3, "Name 3", "email_3@gmail.com", System.currentTimeMillis().toString()),
            UserDto(4, "Name 4", "email_4@gmail.com", System.currentTimeMillis().toString()),
            UserDto(5, "Name 5", "email_5@gmail.com", System.currentTimeMillis().toString()),
            UserDto(6, "Name 6", "email_6@gmail.com", System.currentTimeMillis().toString()),
            UserDto(7, "Name 7", "email_7@gmail.com", System.currentTimeMillis().toString()),
            UserDto(8, "Name 8", "email_8@gmail.com", System.currentTimeMillis().toString())
        )
    }

    override suspend fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ): UserResponse? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        TODO("Not yet implemented")
    }

}
