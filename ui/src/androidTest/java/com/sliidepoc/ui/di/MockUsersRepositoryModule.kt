package com.sliidepoc.ui.di

import com.sliidepoc.data.di.UsersRepositoryModule
import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.ui.mock.data.MockUsersRepository
import com.sliidepoc.ui.mock.data.http.MockUsersProxy
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.10
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [UsersRepositoryModule::class]
)
abstract class MockUsersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUsersRepository(
        usersRepository: MockUsersRepository
    ): UsersRepository

    @Binds
    @Singleton
    abstract fun bindsUsersProxy(
        usersProxy: MockUsersProxy
    ): UsersProxy
}
