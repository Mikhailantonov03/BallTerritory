package com.hfad.data.token.tokens

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    @Singleton
    fun provideTokenStorage(
        impl: TokenStorageImpl
    ): TokenStorage = impl
}
