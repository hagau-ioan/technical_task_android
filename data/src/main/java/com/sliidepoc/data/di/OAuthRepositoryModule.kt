package com.sliidepoc.data.di

import com.sliidepoc.data.OAuthRepositoryImpl
import com.sliidepoc.data.datastore.SettingsStoreImpl
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.datastore.SettingsStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt DI providers for User Handler domain which. All methods from here are connected to methods
 * which connect the UI with ContentProviders or ROOM calls.
 * Nothing else beside User Content Provider or ROOM domain declaration should be add it here.
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class OAuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsOAuthRepository(
        oAuthRepository: OAuthRepositoryImpl
    ): OAuthRepository

    @Binds
    @Singleton
    abstract fun bindsSettingsStore(
        settingsStore: SettingsStoreImpl
    ): SettingsStore
}
