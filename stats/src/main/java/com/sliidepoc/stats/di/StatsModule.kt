package com.sliidepoc.stats.di

import com.sliidepoc.stats.Stats
import com.sliidepoc.stats.StatsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * HILT module related to MW layer. Provide access by using @Inject to some classes.
 *
 * @author Ioan Hagau
 * @since 2020.11.23
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class StatsModule {

    @Binds
    @Singleton
    abstract fun bindsStats(
        stats: StatsImpl
    ): Stats
}
