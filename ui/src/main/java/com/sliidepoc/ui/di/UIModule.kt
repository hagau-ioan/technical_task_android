package com.sliidepoc.ui.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * HILT module for UI layer. Provide access when @Inject is used to some class functionality in the app.
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class UIModule
