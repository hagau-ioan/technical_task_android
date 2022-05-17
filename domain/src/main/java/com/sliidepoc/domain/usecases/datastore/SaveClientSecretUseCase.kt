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
class SaveClientSecretUseCase @Inject constructor(private val OAuthRepository: OAuthRepository) {
    operator fun invoke(clientSecret: String): Flow<String> {
        return OAuthRepository.saveClientSecret(clientSecret)
    }
}
