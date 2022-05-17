package com.sliidepoc.data.di

import com.sliidepoc.data.UsersRepositoryImpl
import com.sliidepoc.data.http.OAuthProxyImpl
import com.sliidepoc.data.http.UsersProxyImpl
import com.sliidepoc.data.http.api.OAuthProxy
import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.domain.api.data.UsersRepository
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
abstract class UsersRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsUsersRepository(
        usersRepository: UsersRepositoryImpl
    ): UsersRepository

    @Binds
    @Singleton
    abstract fun bindsUsersProxy(
        usersProxy: UsersProxyImpl
    ): UsersProxy

    @Binds
    @Singleton
    abstract fun bindsOauthProxy(
        OAuthProxyImpl: OAuthProxyImpl
    ): OAuthProxy
}
