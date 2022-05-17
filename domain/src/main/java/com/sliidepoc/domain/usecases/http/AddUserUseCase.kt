package com.sliidepoc.domain.usecases.http

import com.sliidepoc.domain.api.data.UsersRepository
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
class AddUserUseCase @Inject constructor(private val repository: UsersRepository) {
    operator fun invoke(
        name: String,
        email: String
    ): Flow<Boolean> = flow { emit(repository.addUser(name, email, "male", "active")) }
}
