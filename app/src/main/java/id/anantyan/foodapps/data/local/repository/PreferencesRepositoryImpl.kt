package id.anantyan.foodapps.data.local.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.anantyan.foodapps.common.getValue
import id.anantyan.foodapps.common.setValue
import id.anantyan.foodapps.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow

class PreferencesRepositoryImpl(private val context: Context) : PreferencesRepository {

    companion object {
        private const val DATASTORE_SETTINGS: String = "SETTINGS"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATASTORE_SETTINGS)
        private val TRANSLATE = booleanPreferencesKey("TRANSLATE")
        private val THEME_DAY_NIGHT = booleanPreferencesKey("DAY_NIGHT_THEME")
        private val LOGIN = booleanPreferencesKey("LOGIN")
        private val USER_ID = longPreferencesKey("USER_ID")
    }

    override suspend fun setTranslate(value: Boolean) {
        context.dataStore.setValue(TRANSLATE, value)
    }

    override fun getTranslate(): Flow<Boolean> {
        return context.dataStore.getValue(TRANSLATE, false)
    }

    override suspend fun setTheme(value: Boolean) {
        context.dataStore.setValue(THEME_DAY_NIGHT, value)
    }

    override fun getTheme(): Flow<Boolean> {
        return context.dataStore.getValue(THEME_DAY_NIGHT, false)
    }

    override suspend fun setLogin(value: Boolean) {
        context.dataStore.setValue(LOGIN, value)
    }

    override fun getLogin(): Flow<Boolean> {
        return context.dataStore.getValue(LOGIN, false)
    }

    override suspend fun setUserId(value: Long) {
        context.dataStore.setValue(USER_ID, value)
    }

    override fun getUserId(): Flow<Long> {
        return context.dataStore.getValue(USER_ID, -1L)
    }
}