package com.sliidepoc.data.di

import android.content.Context
import com.sliidepoc.data.room.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
object LocalDbModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): DataBase {
        return DataBase.getInstance(context)
    }

}
