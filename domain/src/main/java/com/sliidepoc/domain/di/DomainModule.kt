package com.sliidepoc.domain.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

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
abstract class DomainModule
