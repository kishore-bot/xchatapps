package com.example.x_chat.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.x_chat.domain.manager.LocalUserManager
import com.example.x_chat.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserManagerImp(
    private val context: Context,
) : LocalUserManager {
    override suspend fun saveAppEntry(token: String) {
        context.dataStore.edit { settings ->
            settings[PreferencesKeys.APP_ENTRY] = token
        }
    }

    override fun readAppEntry(): Flow<String> {
        return context.dataStore.data.map { pref ->
            (if (pref[PreferencesKeys.APP_ENTRY] == "") return@map ""
            else pref[PreferencesKeys.APP_ENTRY].toString())
        }
    }
    override suspend fun deleteAppEntry() {
        context.dataStore.edit { settings ->
            settings.remove(PreferencesKeys.APP_ENTRY)
        }
    }

}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.USER_TOKEN)

private object PreferencesKeys {
    val APP_ENTRY = stringPreferencesKey(name = Constants.APP_ENTRY)
}