package com.sliidepoc.ui.mock.data

import com.sliidepoc.domain.api.data.OAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockOAuthRepository @Inject constructor(): OAuthRepository {

    override fun saveClientId(clientId: String): Flow<String> {
        return flow { emit("client_id") }
    }

    override fun saveClientSecret(clientSecret: String): Flow<String> {
        return flow { emit("client_secret") }
    }

    override fun getClientId(): Flow<String> {
        return flow { emit("client_id") }
    }

    override fun getClientSecret(): Flow<String> {
        return flow { emit("client_secret") }
    }

    override fun resolveOauth(first: String, second: String): Flow<Boolean> {
        return flow { emit(true) }
    }

    override fun refreshOauth(): Flow<Boolean> {
        return flow { emit(true) }
    }
}