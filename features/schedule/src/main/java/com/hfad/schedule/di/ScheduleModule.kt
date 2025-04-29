package com.hfad.schedule.di

import com.hfad.data.token.user.UserStorage
import com.hfad.database.data.BookingDao
import com.hfad.schedule.data.api.ScheduleApi
import com.hfad.schedule.data.repository.BookingRepositoryImpl
import com.hfad.schedule.data.repository.ScheduleRepositoryImpl
import com.hfad.schedule.domain.repository.BookingRepository
import com.hfad.schedule.domain.repository.ScheduleRepository
import com.hfad.schedule.domain.usecase.BookClassUseCase
import com.hfad.schedule.domain.usecase.CancelBookingUseCase
import com.hfad.schedule.domain.usecase.GetScheduleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleModule {

    @Provides
    @Singleton
    fun provideScheduleApi(retrofit: Retrofit): ScheduleApi {
        return retrofit.create(ScheduleApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleRepository(
        api: ScheduleApi
    ): ScheduleRepository = ScheduleRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideBookingRepository(
        api: ScheduleApi,
        userStorage: UserStorage,
        bookingDao: BookingDao
    ): BookingRepository = BookingRepositoryImpl(api, userStorage, bookingDao)

    @Provides
    fun provideGetScheduleUseCase(
        repository: ScheduleRepository
    ): GetScheduleUseCase = GetScheduleUseCase(repository)

    @Provides
    fun provideBookClassUseCase(
        repository: BookingRepository
    ): BookClassUseCase = BookClassUseCase(repository)

    @Provides
    fun provideCancelBookingUseCase(
        repository: BookingRepository
    ): CancelBookingUseCase = CancelBookingUseCase(repository)
}
