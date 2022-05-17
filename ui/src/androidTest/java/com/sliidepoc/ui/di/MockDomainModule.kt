package com.sliidepoc.ui.di

import com.sliidepoc.domain.api.data.mapper.dto.model.UserDto
import com.sliidepoc.domain.di.DomainModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 *
 * @author Hagău Ioan
 * @since 2022.02.10
 */
@Module
@TestInstallIn(
    components = [SingletonComponent::class], replaces = [DomainModule::class]
)
object MockDomainModule {

    @Provides
    @Singleton
    fun provideUserDtoMapper(): DtoMapper<User, UserDto> {
        return UserDtoMapper()
    }
}
