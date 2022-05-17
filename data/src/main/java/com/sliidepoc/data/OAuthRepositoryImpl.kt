package com.sliidepoc.data

import com.sliidepoc.data.http.api.OAuthProxy
import com.sliidepoc.data.http.oauth.SessionManager
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.datastore.SettingsStore
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
class OAuthRepositoryImpl @Inject constructor(
    private val settingsStore: SettingsStore,
    private val oAuthProxy: OAuthProxy,
    private val sessionManager: SessionManager
) : OAuthRepository {

    override fun saveClientId(clientId: String): Flow<String> {

        // Using the keyStore encryption we will save the clientId into the data store.

        return settingsStore.saveClientId(clientId)
    }

    override fun saveClientSecret(clientSecret: String): Flow<String> {

        // Using the keyStore encryption we will save the client secret into the data store.

        return settingsStore.saveClientSecret(clientSecret)
    }

    override fun getClientId(): Flow<String> {

        // Using the keyStore encryption we will get the clientId from the data store.

        return flow { emit("client_id") } //settingsStore.getClientId()
    }

    override fun getClientSecret(): Flow<String> {

        // Using the keyStore encryption we will get the clientSecret from the data store.

        return flow { emit("client_secret") }// settingsStore.getClientSecret()
    }

    override fun resolveOauth(clientId: String, clientSecret: String): Flow<Boolean> {
        return flow {
            val credentials = oAuthProxy.loadCredentials(clientId, clientSecret)
            credentials?.first?.let {
                sessionManager.credentials = credentials
                oAuthProxy.loadToken(it, credentials.second)?.let { token ->
                    sessionManager.token = token
                    emit(token.isNotEmpty())
                } ?: emit(false)
            } ?: emit(false)
        }
    }

    override fun refreshOauth(): Flow<Boolean> {
        return flow {
            sessionManager.token = null
            if (sessionManager.credentials?.first != null && sessionManager.credentials?.second != null) {
                oAuthProxy.loadToken(
                    sessionManager.credentials?.first!!,
                    sessionManager.credentials?.second!!
                )?.let { token ->
                    sessionManager.token = token
                    emit(token.isNotEmpty())
                } ?: emit(false)
            } else {
                emit(false)
            }
        }
    }
}
