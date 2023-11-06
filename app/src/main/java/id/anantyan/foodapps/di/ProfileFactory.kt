package id.anantyan.foodapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.presentation.profile.ProfileViewModel
import id.anantyan.foodapps.presentation.register.RegisterViewModel

class ProfileFactory(
    private val userUseCase: UserUseCase,
    private val preferencesUseCase: PreferencesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(preferencesUseCase, userUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}