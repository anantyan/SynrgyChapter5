package id.anantyan.foodapps.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingViewModel(private val preferencesUseCase: PreferencesUseCase) : ViewModel() {
    val getTheme: Flow<Boolean> = preferencesUseCase.executeGetTheme()
    val getTranslate: Flow<Boolean> = preferencesUseCase.executeGetTranslate()

    fun setTheme(value: Boolean) {
        viewModelScope.launch {
            preferencesUseCase.executeSetTheme(value)
        }
    }

    fun setTranslate(value: Boolean) {
        viewModelScope.launch {
            preferencesUseCase.executeSetTranslate(value)
        }
    }
}