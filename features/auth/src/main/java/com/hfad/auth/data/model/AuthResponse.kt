package com.hfad.auth.data.model


data class AuthResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val isNewUser: Boolean
)
