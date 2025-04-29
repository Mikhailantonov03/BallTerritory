package com.hfad.data.token.tokens

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenStorage {

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    }

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val IS_GUEST = "is_guest"
    }

    override suspend fun setGuestMode(enabled: Boolean) {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putBoolean(IS_GUEST, enabled)
            }
        }
    }

    override suspend fun isGuestMode(): Boolean {
        return withContext(Dispatchers.IO) {
            prefs.getBoolean(IS_GUEST, false)
        }
    }

    override suspend fun saveTokens(access: String, refresh: String) {
        withContext(Dispatchers.IO) {
            prefs.edit {
                putString(ACCESS_TOKEN, access)
                putString(REFRESH_TOKEN, refresh)
            }
        }
    }

    override suspend fun getAccessToken(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(ACCESS_TOKEN, null)
        }
    }

    override suspend fun getRefreshToken(): String? {
        return withContext(Dispatchers.IO) {
            prefs.getString(REFRESH_TOKEN, null)
        }
    }

    override suspend fun clearTokens() {
        withContext(Dispatchers.IO) {
            prefs.edit {
                remove(ACCESS_TOKEN)
                remove(REFRESH_TOKEN)
                remove(IS_GUEST)
            }
        }
    }
}
