package com.jax.movies.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class OnBoardingSettingStore(
    private val context: Context
) {
    val Context.dataStore by preferencesDataStore(OnBoardingSettingFileName)

    val isEnteredFlow = context.dataStore.data.map {
        it[isEnteredKey] ?: false
    }

    suspend fun updateIsEntered(isEntered: Boolean) {
        context.dataStore.edit {
            it[isEnteredKey] = isEntered
        }
    }

    companion object {
        val isEnteredKey = booleanPreferencesKey("is_entered")
        private const val OnBoardingSettingFileName = "file_name_onboarding"
    }
}