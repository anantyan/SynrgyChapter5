package id.anantyan.foodapps.domain.repository

import kotlinx.coroutines.flow.Flow

class PreferencesUseCase(private val preferencesRepository: PreferencesRepository) {
    suspend fun executeSetTranslate(value: Boolean) = preferencesRepository.setTranslate(value)
    fun executeGetTranslate(): Flow<Boolean> = preferencesRepository.getTranslate()
    suspend fun executeSetTheme(value: Boolean) = preferencesRepository.setTheme(value)
    fun executeGetTheme(): Flow<Boolean> = preferencesRepository.getTheme()
    suspend fun executeSetLogin(value: Boolean) = preferencesRepository.setLogin(value)
    fun executeGetLogin(): Flow<Boolean> = preferencesRepository.getLogin()
    suspend fun executeSetUsrId(value: Long) = preferencesRepository.setUserId(value)
    fun executeGetUserId(): Flow<Long> = preferencesRepository.getUserId()
}