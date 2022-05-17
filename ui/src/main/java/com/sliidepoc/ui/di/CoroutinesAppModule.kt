package com.sliidepoc.ui.di

import com.steelcase.common.utils.CoroutineContextThread
import com.steelcase.common.utils.CoroutineScopeApp
import com.steelcase.common.utils.CoroutineScopeAppImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/**
 * HILT module for UI layer. Provide access when @Inject is used to some class functionality in the app.
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesAppModule {

    @Provides
    @Singleton
    fun provideCoroutineContextThread(): CoroutineContextThread {
        return CoroutineContextThread()
    }

    @Provides
    fun provideCoroutineScopeApp(coroutineContextThread: CoroutineContextThread): CoroutineScopeApp {
        return CoroutineScopeAppImpl(
            CoroutineScope(coroutineContextThread.io),
            coroutineContextThread
        )
    }
}
