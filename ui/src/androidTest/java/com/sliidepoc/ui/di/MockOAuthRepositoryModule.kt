package com.sliidepoc.ui.di

import com.sliidepoc.data.di.OAuthRepositoryModule
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.datastore.SettingsStore
import com.sliidepoc.ui.mock.data.MockOAuthRepository
import com.sliidepoc.ui.mock.data.oauth.MockSettingsStore
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
    components = [SingletonComponent::class], replaces = [OAuthRepositoryModule::class]
)
abstract class MockOAuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsOAuthRepository(
        oAuthRepository: MockOAuthRepository
    ): OAuthRepository

    @Binds
    @Singleton
    abstract fun bindsSettingsStore(
        settingsStore: MockSettingsStore
    ): SettingsStore
}
