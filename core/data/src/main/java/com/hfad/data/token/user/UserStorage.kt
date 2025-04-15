package com.hfad.data.token.user
import com.hfad.module.User


interface UserStorage {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun clearUser()
}
