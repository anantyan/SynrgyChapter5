package id.anantyan.foodapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.presentation.change_profile.ChangeProfileViewModel
import id.anantyan.foodapps.presentation.home.HomeViewModel

class ChangeProfileFactory(
    private val preferencesUseCase: PreferencesUseCase,
    private val userUseCase: UserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChangeProfileViewModel::class.java)) {
            return ChangeProfileViewModel(userUseCase, preferencesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}