package com.hfad.schedule.data.api

import com.hfad.schedule.data.model.ScheduleItemDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ScheduleApi {

    @GET("/auth/schedule")
    suspend fun getSchedule(): List<ScheduleItemDto>

    @POST("/auth/book")
    suspend fun bookClass(@Query("scheduleId") scheduleId: String): Unit

    @DELETE("/auth/book")
    suspend fun cancelBooking(@Query("scheduleId") scheduleId: String): Unit
}
