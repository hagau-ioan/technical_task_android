package com.sliidepoc.domain.api.data.datastore

import kotlinx.coroutines.flow.Flow

/**
 * Expose any functionality related to SettingsStore. This will be related to any user preference choice.
 *
 * @author Ioan Hagau
 * @since 2020.11.30
 */
interface SettingsStore {

    fun saveClientId(clientId: String): Flow<String>

    fun saveClientSecret(clientSecret: String): Flow<String>

    fun getClientId(): Flow<String>

    fun getClientSecret(): Flow<String>
}
