package com.hfad.auth.domain.model

import com.hfad.module.AuthTokens
import com.hfad.module.User

sealed class SaveResult {
    data class NewUser(val tokens: AuthTokens, val user: User) : SaveResult()
    data class ExistingUser(val accessToken: String) : SaveResult()
}
