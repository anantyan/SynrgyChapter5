package id.anantyan.foodapps.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun setTheme(value: Boolean)
    fun getTheme(): Flow<Boolean>
    suspend fun setLogin(value: Boolean)
    fun getLogin(): Flow<Boolean>
    suspend fun setUserId(value: Long)
    fun getUserId(): Flow<Long>
}