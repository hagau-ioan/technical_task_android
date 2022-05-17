package com.sliidepoc.ui.di

import com.sliidepoc.common.utils.CoroutineContextThread
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import javax.inject.Singleton

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.10
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [CoroutinesModule::class]
)
object MockCoroutinesModule {

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    @Singleton
    fun provideCoroutineContextThread(): CoroutineContextThread {
        // UnconfinedTestDispatcher()
        return CoroutineContextThread(
            io = StandardTestDispatcher(),
            default = StandardTestDispatcher()
        )
    }
}
