package com.sliidepoc.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.sliidepoc.common.datastore.LocalDataStore
import com.sliidepoc.domain.api.data.datastore.SettingsStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Concrete implementation of SettingsStoreHandler which expose access to DataStore
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@Singleton
class SettingsStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localDataStore: LocalDataStore
) : SettingsStore {

    private val dataStore: DataStore<Preferences>
        get() = context.createDataStore(name = SettingsStoreModel.SETTINGS.key)

    override fun saveClientId(clientId: String): Flow<String> {
        return flow {
            localDataStore.put(SettingsStoreModel.Keys.CLIENT_ID.key, clientId, dataStore)
            emit(clientId)
        }
    }

    override fun saveClientSecret(clientSecret: String): Flow<String> {
        return flow {
            localDataStore.put(SettingsStoreModel.Keys.CLIENT_SECRET.key, clientSecret, dataStore)
            emit(clientSecret)
        }
    }

    override fun getClientId(): Flow<String> {
        return localDataStore.getString(SettingsStoreModel.Keys.CLIENT_ID.key, dataStore)
    }

    override fun getClientSecret(): Flow<String> {
        return localDataStore.getString(SettingsStoreModel.Keys.CLIENT_SECRET.key, dataStore)
    }
}
