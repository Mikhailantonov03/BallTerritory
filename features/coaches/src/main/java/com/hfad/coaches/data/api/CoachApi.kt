package com.hfad.coaches.data.api

import com.hfad.coaches.data.model.CoachesResponse
import retrofit2.http.GET

interface CoachApi {
    @GET("coaches")
    suspend fun getCoaches(): CoachesResponse
}

