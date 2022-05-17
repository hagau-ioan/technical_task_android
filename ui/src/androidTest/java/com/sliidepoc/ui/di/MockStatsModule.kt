package com.sliidepoc.ui.di

import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.di.StatsModule
import com.sliidepoc.ui.mock.utils.MockStats
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.02.10
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [StatsModule::class]
)
object MockStatsModule {

    @Provides
    @Singleton
    fun provideStats(): Stats {
        return MockStats()
    }
}
