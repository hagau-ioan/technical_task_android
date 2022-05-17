package com.sliidepoc.domain.usecases.datastore

import com.sliidepoc.domain.api.data.OAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
@Singleton
class SaveClientIdUseCase @Inject constructor(private val repository: OAuthRepository) {
    operator fun invoke(clientId: String): Flow<String> = repository.saveClientId(clientId)
}
