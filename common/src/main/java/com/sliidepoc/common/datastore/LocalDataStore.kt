@file:Suppress("unused")

package com.sliidepoc.common.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.sliidepoc.common.utils.formater.StringUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Base class to work with store data by doing CRUD  operations with different type of data.
 * Note: Any removal of a key from data store will require a clear cache data of the app
 *
 * @author Ioan Hagau
 * @since 2020.11.30
 */
@Singleton
class LocalDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getString(key: String, dataStore: DataStore<Preferences>): Flow<String> {
        val searchKey = preferencesKey<String>(key)
        return dataStore.data
            .map { preferences ->
                preferences[searchKey] ?: StringUtils.EMPTY_STRING
            }
    }

    fun getInt(key: String, dataStore: DataStore<Preferences>): Flow<Int> {
        val searchKey = preferencesKey<Int>(key)
        return dataStore.data
            .map { preferences ->
                preferences[searchKey] ?: 0
            }
    }

    fun getLong(key: String, dataStore: DataStore<Preferences>): Flow<Long> {
        val searchKey = preferencesKey<Long>(key)
        return dataStore.data
            .map { preferences ->
                preferences[searchKey] ?: 0
            }
    }

    fun getBoolean(key: String, dataStore: DataStore<Preferences>): Flow<Boolean> {
        val searchKey = preferencesKey<Boolean>(key)
        return dataStore.data
            .map { preferences ->
                preferences[searchKey] ?: false
            }
    }

    suspend fun put(key: String, value: Int, dataStore: DataStore<Preferences>) {
        val searchKey = preferencesKey<Int>(key)
        dataStore.edit { preferences ->
            preferences[searchKey] = value
        }
    }

    suspend fun put(key: String, value: String, dataStore: DataStore<Preferences>) {
        val searchKey = preferencesKey<String>(key)
        dataStore.edit { preferences ->
            preferences[searchKey] = value
        }
    }

    suspend fun put(key: String, value: Boolean, dataStore: DataStore<Preferences>) {
        val searchKey = preferencesKey<Boolean>(key)
        dataStore.edit { preferences ->
            preferences[searchKey] = value
        }
    }

    suspend fun put(key: String, value: Long, dataStore: DataStore<Preferences>) {
        val searchKey = preferencesKey<Long>(key)
        dataStore.edit { preferences ->
            preferences[searchKey] = value
        }
    }
}
