package com.hfad.module

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean,

)
