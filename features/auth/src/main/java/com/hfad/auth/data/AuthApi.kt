package com.hfad.auth.data


import com.hfad.auth.data.model.AuthResponse
import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.auth.data.model.ProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/send")
    suspend fun sendPhoneNumber(@Body request: PhoneRequest): Response<Unit>

    @POST("/auth/verify")
    suspend fun verifyCode(@Body request: CodeRequest): Response<AuthResponse>

    @POST("auth/profile")
    suspend fun completeProfile(@Body request: ProfileRequest): Response<Unit>
}

