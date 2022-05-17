package com.sliidepoc.ui.di

import com.sliidepoc.common.utils.CoroutineContextThread
import com.sliidepoc.common.utils.CoroutineScopeApp
import com.sliidepoc.common.utils.CoroutineScopeAppImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineScope
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
    components = [SingletonComponent::class], replaces = [CoroutinesAppModule::class]
)
object MockCoroutinesAppModule {

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Provides
    fun provideCoroutineScopeApp(coroutineContextThread: CoroutineContextThread): CoroutineScopeApp {
        // TestScope(coroutineContextThread.io)
        return CoroutineScopeAppImpl(CoroutineScope(coroutineContextThread.io), coroutineContextThread)
    }
}
