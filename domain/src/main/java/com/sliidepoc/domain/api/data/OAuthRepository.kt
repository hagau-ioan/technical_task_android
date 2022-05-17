package com.sliidepoc.domain.api.data

import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
interface OAuthRepository {

    fun saveClientId(clientId: String): Flow<String>

    fun saveClientSecret(clientSecret: String): Flow<String>

    fun getClientId(): Flow<String>

    fun getClientSecret(): Flow<String>

    fun resolveOauth(first: String, second: String): Flow<Boolean>

    fun refreshOauth(): Flow<Boolean>
}
