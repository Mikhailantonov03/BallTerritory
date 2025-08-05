package com.hfad.schedule.data.api

import com.hfad.schedule.data.model.ScheduleItemDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApi {

    @GET("schedule")
    suspend fun getSchedule(): List<ScheduleItemDto>

    @POST("book")
    suspend fun bookClass(@Query("scheduleId") scheduleId: String): Unit

    @DELETE("book")
    suspend fun cancelBooking(@Query("scheduleId") scheduleId: String): Unit
}
