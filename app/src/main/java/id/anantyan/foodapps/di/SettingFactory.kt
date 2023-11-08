package id.anantyan.foodapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.presentation.change_profile.ChangeProfileViewModel
import id.anantyan.foodapps.presentation.home.HomeViewModel
import id.anantyan.foodapps.presentation.setting.SettingViewModel

class SettingFactory(
    private val preferencesUseCase: PreferencesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(preferencesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}