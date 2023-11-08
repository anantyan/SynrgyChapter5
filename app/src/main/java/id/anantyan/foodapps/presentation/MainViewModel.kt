package id.anantyan.foodapps.presentation

import androidx.lifecycle.ViewModel
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainViewModel(
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    fun getLogin(): Boolean {
        return runBlocking { preferencesUseCase.executeGetLogin().first() }
    }

    fun getTheme(): Flow<Boolean> {
        return preferencesUseCase.executeGetTheme()
    }

    fun getTranslate(): Flow<Boolean> {
        return preferencesUseCase.executeGetTranslate()
    }
}