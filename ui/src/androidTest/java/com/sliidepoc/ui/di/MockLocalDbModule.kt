package com.sliidepoc.ui.di

import android.content.Context
import androidx.room.Room
import com.sliidepoc.data.di.LocalDbModule
import com.sliidepoc.data.room.database.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
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
    components = [SingletonComponent::class], replaces = [LocalDbModule::class]
)
object MockLocalDbModule {
    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): DataBase {
        return Room.inMemoryDatabaseBuilder(context, DataBase::class.java).build()
    }
}
