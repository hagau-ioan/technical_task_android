package com.sliidepoc.ui.mock.data

import com.sliidepoc.data.room.database.DataBase
import com.sliidepoc.data.room.model.UserImpl
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.datastore.SettingsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockOAuthRepository constructor(
    private val settingsStore: SettingsStore,
    private val dataBase: DataBase
) : OAuthRepository {

    override fun saveTC(datetime: Long): Flow<Boolean> {
        return settingsStore.saveTCSuccess(datetime)
    }

    override fun isTCSaved(): Flow<Long> {
        return settingsStore.isTCConfirmed()
    }

    override fun insertUser(
        nickName: String,
        userName: String,
        userPassword: String,
        userEmail: String
    ): Flow<Long> {
        return flow {
            emit(
                dataBase.userDao.insert(
                    UserImpl(
                        user_nickname = nickName,
                        user_email = userEmail,
                        user_userName = userName,
                        user_password = userPassword
                    )
                )
            )
        }
    }

    override fun updateUser(
        userId: Long,
        nickName: String,
        userName: String,
        userPassword: String,
        userEmail: String
    ): Flow<Int> {
        return flow {
            emit(
                dataBase.userDao.update(
                    UserImpl(
                        user_userId = userId,
                        user_nickname = nickName,
                        user_email = userEmail,
                        user_userName = userName,
                        user_password = userPassword

                    )
                )
            )
        }
    }

    override fun getUserDetails(userId: Long): Flow<User> {
        return dataBase.userDao.getUser(userId)
    }

    override fun getAllUsers(): Flow<List<User>> {
        return dataBase.userDao.getUsers()
    }

    override fun countUsers(): Flow<Int> {
        return flow { emit(dataBase.userDao.countUsers()) }
    }
}
