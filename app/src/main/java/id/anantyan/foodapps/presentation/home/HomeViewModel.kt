package id.anantyan.foodapps.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val preferencesUseCase: PreferencesUseCase
) : ViewModel() {
    fun setLogin(value: Boolean) {
        viewModelScope.launch {
            preferencesUseCase.executeSetLogin(value)
        }
    }
}