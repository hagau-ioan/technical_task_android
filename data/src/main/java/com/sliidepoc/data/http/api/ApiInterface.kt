package com.sliidepoc.data.http.api

import com.sliidepoc.data.http.models.PostActionResponse
import com.sliidepoc.data.http.models.UserRequest
import com.sliidepoc.data.http.models.UsersListResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Define the url requests used into WSPProxy class file. This interface is built according to Retrofit documentation.
 * Another API example source: https://dog.ceo/dog-api/documentation/
 * @author Ioan Hagau
 * @since 2020.11.26
 */

interface ApiInterface {

    @GET("users")
    suspend fun getUsers(@Query("page") page: Int? = null): Response<UsersListResponse>

    @POST("users")
    @Headers("Content-Type: application/json")
    suspend fun addUser(
        @Body user: UserRequest
    ): Response<PostActionResponse>

    @DELETE("users/{userId}")
    @Headers("Content-Type: application/json")
    suspend fun deleteUser(
        @Path("userId") userId: Int
    ): Response<PostActionResponse>
}
