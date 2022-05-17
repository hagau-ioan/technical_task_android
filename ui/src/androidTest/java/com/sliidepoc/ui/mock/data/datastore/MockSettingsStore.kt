package com.sliidepoc.ui.mock.data.datastore

import com.sliidepoc.domain.api.data.datastore.SettingsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.03
 */
class MockSettingsStore : SettingsStore {

    override fun saveTCSuccess(dataTime: Long): Flow<Boolean> {
        return flow {
            emit(true)
        }
    }

    override fun isTCConfirmed(): Flow<Long> {
        return flow {
            emit(1)
        }
    }
}
