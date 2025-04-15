package com.hfad.data.token.user




import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserStorageModule {

    @Binds
    @Singleton
    abstract fun bindUserStorage(
        impl: UserStorageImpl
    ): UserStorage
}
