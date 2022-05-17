package com.sliidepoc.data.http

import android.content.Context
import com.sliidepoc.common.retrofit.exception.BackendException
import com.sliidepoc.common.utils.ext.TAG
import com.sliidepoc.data.http.adapter.RetrofitServiceAdapter
import com.sliidepoc.data.http.api.HttpResponseCodes
import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.data.http.models.UserRequest
import com.sliidepoc.data.http.models.UserResponse
import com.sliidepoc.data.http.models.dto.UserDtoMapper
import com.sliidepoc.data.http.oauth.SessionManager
import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsEventsLogs
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@Suppress("unused")
@Singleton
class UsersProxyImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val retrofitService: RetrofitServiceAdapter,
    private val stats: Stats,
    private val userDtoMapper: UserDtoMapper,
    private val sessionManager: SessionManager
) : UsersProxy {

    companion object {
        const val NO_PAGES = 0
    }

    override suspend fun getUsersFromLastPage(): List<UserDto> {
        val nrOfPages = getNrOfPages()
        if (nrOfPages > 0) {
            return getUsers(nrOfPages)
        }
        return emptyList()
    }

    override suspend fun getUsers(page: Int): List<UserDto> {
        try {
            val response = retrofitService.commonApiInterface.getUsers(page)
            response.run {
                if (!isSuccessful || body() == null) {
                    stats.sendLogEvent(StatsEventsLogs.E(TAG, "${code()}, ${message()}"))
                    throw BackendException(code(), message())
                }
                return this.body()?.data?.map { userDtoMapper.mapToDtoModel(it) }?.toList()
                    ?: emptyList()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return emptyList()
    }

    override suspend fun addUser(
        name: String,
        email: String,
        gender: String,
        status: String
    ): UserResponse? {
        try {
            val response = retrofitService.commonApiInterface.addUser(
                UserRequest(name, email, gender, status)
            )
            response.run {
                if (!isSuccessful || body() == null) {
                    stats.sendLogEvent(StatsEventsLogs.E(TAG, "${code()}, ${message()}"))
                    throw BackendException(code(), message())
                }
                if (isOperationSuccessful(code())) {
                    return this.body()?.data
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override suspend fun deleteUser(userId: Int): Boolean {
        var success = false
        try {
            val response = retrofitService.commonApiInterface.deleteUser(userId)
            response.run {
                if (!isSuccessful || body() == null) {
                    stats.sendLogEvent(StatsEventsLogs.E(TAG, "${code()}, ${message()}"))
                    success = isOperationSuccessful(code())
                    throw BackendException(code(), message())
                }
                success = isOperationSuccessful(code())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return success
    }

    private suspend fun getNrOfPages(): Int {
        try {
            val response = retrofitService.commonApiInterface.getUsers()
            response.run {
                if (!isSuccessful || body() == null) {
                    stats.sendLogEvent(StatsEventsLogs.E(TAG, "${code()}, ${message()}"))
                    throw BackendException(code(), message())
                }
                return this.body()?.metaInfoResponse?.pagResponse?.pages ?: NO_PAGES
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return NO_PAGES
    }

    private fun isOperationSuccessful(code: Int): Boolean {
        return when (code) {
            HttpResponseCodes.ResourceDeleted.code -> true
            HttpResponseCodes.ResourceCreated.code -> true
            HttpResponseCodes.ResourceNotExist.code -> true
            else -> false
        }
    }
}
