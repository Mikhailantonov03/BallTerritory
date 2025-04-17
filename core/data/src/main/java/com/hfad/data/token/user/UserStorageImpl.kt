package com.hfad.data.token.user


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hfad.module.User
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

private val Context.userDataStore by preferencesDataStore(name = "user_prefs")

@Singleton
class UserStorageImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserStorage {

    companion object {
        private val USER_ID = stringPreferencesKey("user_id")
        private val USER_PHONE = stringPreferencesKey("user_phone")
        private val USER_NAME = stringPreferencesKey("user_name")
        private val USER_EMAIL = stringPreferencesKey("user_email")

    }

    override suspend fun saveUser(user: User) {
        context.userDataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USER_PHONE] = user.phone
            prefs[USER_NAME] = user.name ?: ""
            prefs[USER_EMAIL] = user.email ?: ""

        }
    }

    override suspend fun getUser(): User? {
        val prefs = context.userDataStore.data.firstOrNull() ?: return null
        val id = prefs[USER_ID] ?: return null
        val phone = prefs[USER_PHONE] ?: return null
        val name = prefs[USER_NAME]
        val email = prefs[USER_EMAIL]
        return User(id = id, phone = phone, name = name, email = email)
    }

    override suspend fun clearUser() {
        context.userDataStore.edit { prefs ->
            prefs.remove(USER_ID)
            prefs.remove(USER_PHONE)
            prefs.remove(USER_NAME)
            prefs.remove(USER_EMAIL)

        }
    }
}

