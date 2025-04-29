package com.hfad.data.token.user

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.hfad.module.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserStorage {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    companion object {
        private const val USER_ID = "user_id"
        private const val USER_PHONE = "user_phone"
        private const val USER_NAME = "user_name"
        private const val USER_EMAIL = "user_email"
    }

    override suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putString(USER_ID, user.id)
                putString(USER_PHONE, user.phone)
                putString(USER_NAME, user.name ?: "")
                putString(USER_EMAIL, user.email ?: "")
            }
        }
    }

    override suspend fun getUser(): User? {
        return withContext(Dispatchers.IO) {
            val id = prefs.getString(USER_ID, null) ?: return@withContext null
            val phone = prefs.getString(USER_PHONE, null) ?: return@withContext null
            val name = prefs.getString(USER_NAME, null)
            val email = prefs.getString(USER_EMAIL, null)

            User(id = id, phone = phone, name = name, email = email)
        }
    }

    override suspend fun clearUser() {
        withContext(Dispatchers.IO) {
            prefs.edit {
                remove(USER_ID)
                remove(USER_PHONE)
                remove(USER_NAME)
                remove(USER_EMAIL)
            }
        }
    }
}
