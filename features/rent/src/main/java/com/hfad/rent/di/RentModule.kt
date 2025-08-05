package com.hfad.rent.di

import com.hfad.rent.data.RentRepositoryImpl
import com.hfad.rent.data.api.RentApi
import com.hfad.rent.domain.repository.RentRepository
import com.hfad.rent.domain.usecase.SubmitRentalRequestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RentModule {


    @Provides
    @Singleton
    fun provideRentApi(retrofit: Retrofit): RentApi {
        return retrofit.create(RentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRentRepository(
        api: RentApi
    ): RentRepository {
        return RentRepositoryImpl(api)
    }

    @Provides
    fun provideSubmitRentalRequestUseCase(
        repository: RentRepository
    ): SubmitRentalRequestUseCase {
        return SubmitRentalRequestUseCase(repository)
    }
}

