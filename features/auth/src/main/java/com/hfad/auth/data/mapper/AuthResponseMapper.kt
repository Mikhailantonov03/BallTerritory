package com.hfad.auth.data.mapper

import com.hfad.auth.data.model.AuthResponse
import com.hfad.module.AuthTokens
import com.hfad.module.User

fun AuthResponse.toDomain(phoneFromInput: String): Pair<User, AuthTokens> {
    return Pair(
        User(
            id = userId,
            phone = phoneFromInput
        ),
        AuthTokens(
            accessToken = accessToken,
            refreshToken = refreshToken,
            isNewUser = isNewUser
        )
    )
}
