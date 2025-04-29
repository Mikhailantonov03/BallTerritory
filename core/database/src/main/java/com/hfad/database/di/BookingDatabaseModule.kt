package com.hfad.database.di

import android.content.Context
import androidx.room.Room
import com.hfad.database.data.BookingDao
import com.hfad.database.data.BookingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object BookingDatabaseModule {

    @Provides
    @Singleton
    fun provideBookingDatabase(
        @ApplicationContext context: Context
    ): BookingDatabase {
        return Room.databaseBuilder(
            context,
            BookingDatabase::class.java,
            "booking_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookingDao(
        db: BookingDatabase
    ): BookingDao {
        return db.bookingDao()
    }
}
