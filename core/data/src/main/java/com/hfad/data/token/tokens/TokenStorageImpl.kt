package com.hfad.data.token.tokens
import androidx.datastore.preferences.preferencesDataStore
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "auth_prefs")

@Singleton
class TokenStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenStorage {

    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        private val IS_GUEST = stringPreferencesKey("is_guest")

    }


    override suspend fun setGuestMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_GUEST] = enabled.toString()
        }
    }

    override suspend fun isGuestMode(): Boolean {
        return context.dataStore.data
            .map { prefs -> prefs[IS_GUEST] == "true" }
            .firstOrNull() ?: false
    }



    override suspend fun saveTokens(access: String, refresh: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = access
            prefs[REFRESH_TOKEN] = refresh
        }
    }

    override suspend fun getAccessToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[ACCESS_TOKEN] }
            .firstOrNull()
    }

    override suspend fun getRefreshToken(): String? {
        return context.dataStore.data
            .map { prefs -> prefs[REFRESH_TOKEN] }
            .firstOrNull()
    }

    override suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
    }
}