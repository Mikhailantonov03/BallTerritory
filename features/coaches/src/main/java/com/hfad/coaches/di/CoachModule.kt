package com.hfad.coaches.di

import com.hfad.coaches.data.api.CoachApi
import com.hfad.coaches.data.repository.CoachRepositoryImpl
import com.hfad.coaches.domain.repository.CoachRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoachModule {

    @Provides
    @Singleton
    fun provideCoachApi(retrofit: Retrofit): CoachApi =
        retrofit.create(CoachApi::class.java)

    @Provides
    @Singleton
    fun provideCoachRepository(api: CoachApi): CoachRepository =
        CoachRepositoryImpl(api)
}
