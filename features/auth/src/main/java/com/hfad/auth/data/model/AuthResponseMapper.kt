package com.hfad.auth.data.model

import com.hfad.module.AuthTokens
import com.hfad.module.User

fun AuthResponse.toDomain(phoneFromInput: String): Pair<User, AuthTokens> {
    return User(
        id = userId,
        phone = phoneFromInput
    ) to AuthTokens(
        accessToken = accessToken,
        refreshToken = refreshToken,
        isNewUser = isNewUser
    )
}

