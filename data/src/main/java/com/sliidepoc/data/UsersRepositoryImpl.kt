package com.sliidepoc.data

import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.data.room.database.DataBase
import com.sliidepoc.data.room.model.UserImpl
import com.sliidepoc.data.room.model.dto.UserDtoMapper
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
class UsersRepositoryImpl @Inject constructor(
    private val usersProxy: UsersProxy,
    private val dataBase: DataBase,
    private val userDtoMapper: UserDtoMapper
) : UsersRepository {

    override suspend fun getUsersFromLastPage(): List<UserDto> {
        val dbUsers = dataBase.userDao.getUsers().map { userDtoMapper.mapToDtoModel(it) }.toList()
        val httpUsers = usersProxy.getUsersFromLastPage()
        if (dbUsers.isNotEmpty()) {
            return httpUsers + dbUsers
        }
        return httpUsers
    }

    override suspend fun getUsers(page: Int): List<UserDto> {
        return usersProxy.getUsers(page)
    }

    override suspend fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ): Boolean {
        val addedUser = usersProxy.addUser(name, email, gender, status)
        return addedUser?.let {
            dataBase.userDao.insert(
                UserImpl(
                    extId = addedUser.id,
                    email = addedUser.email,
                    name = addedUser.name,
                    gender = addedUser.gender,
                    status = addedUser.status,
                    creationDateTime = System.currentTimeMillis().toString()
                )
            )
            true
        } ?: false
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        if (usersProxy.deleteUser(userId)) {
            dataBase.userDao.delete(userId)
            return true
        }
        return false
    }

    override fun countSavedUsers(): Flow<Int> {
        return flow { emit(dataBase.userDao.countUsers()) }
    }
}
