package com.sliidepoc.ui.di

import android.content.Context
import androidx.room.Room
import com.sliidepoc.data.di.DataModule
import com.sliidepoc.data.http.api.UsersProxy
import com.sliidepoc.data.room.database.DataBase
import com.sliidepoc.domain.api.data.OAuthRepository
import com.sliidepoc.domain.api.data.UsersRepository
import com.sliidepoc.domain.api.data.datastore.SettingsStore
import com.sliidepoc.ui.mock.data.MockOAuthRepository
import com.sliidepoc.ui.mock.data.MockUsersRepository
import com.sliidepoc.ui.mock.data.datastore.MockSettingsStore
import com.sliidepoc.ui.mock.data.http.MockUsersProxy
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
    components = [SingletonComponent::class], replaces = [DataModule::class]
)
object MockDataModule {

    @Provides
    @Singleton
    fun provideLocalRepository(
        settingsStore: SettingsStore,
        dataBase: DataBase
    ): OAuthRepository {
        return MockOAuthRepository(settingsStore, dataBase)
    }

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context
    ): DataBase {
        return Room.inMemoryDatabaseBuilder(context, DataBase::class.java).build()
    }

    @Provides
    @Singleton
    fun provideSettingsStore(): SettingsStore {
        return MockSettingsStore()
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(
        usersProxy: UsersProxy
    ): UsersRepository {
        return MockUsersRepository(usersProxy)
    }

    @Provides
    fun provideNewsProxy(): UsersProxy {
        return MockUsersProxy()
    }
}
