package com.hfad.data.token.tokens

interface TokenStorage {
    suspend fun saveTokens(access: String, refresh: String)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
    suspend fun clearTokens()
}
