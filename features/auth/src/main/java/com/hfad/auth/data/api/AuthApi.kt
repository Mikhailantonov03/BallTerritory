package com.hfad.auth.data.api


import com.hfad.auth.data.model.AuthResponse
import com.hfad.auth.data.model.CodeRequest
import com.hfad.auth.data.model.PhoneRequest
import com.hfad.auth.data.model.ProfileRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/send")
    suspend fun sendPhoneNumber(@Body request: PhoneRequest): Response<Unit>

    @POST("/verify")
    suspend fun verifyCode(@Body request: CodeRequest): Response<AuthResponse>

    @POST("/profile")
    suspend fun completeProfile(@Body request: ProfileRequest): Response<Unit>
}

