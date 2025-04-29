package com.hfad.profile.di

import com.hfad.database.data.BookingDao
import com.hfad.profile.data.BookingHistoryRepositoryImpl
import com.hfad.profile.domain.repository.BookingHistoryRepository
import com.hfad.profile.domain.usecase.GetBookingHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    fun provideBookingHistoryRepository(bookingDao: BookingDao): BookingHistoryRepository {
        return BookingHistoryRepositoryImpl(bookingDao)
    }

    @Provides
    fun provideGetBookingHistoryUseCase(repository: BookingHistoryRepository): GetBookingHistoryUseCase {
        return GetBookingHistoryUseCase(repository)
    }
}
