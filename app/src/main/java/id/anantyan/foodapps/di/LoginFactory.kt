package id.anantyan.foodapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import id.anantyan.foodapps.presentation.login.LoginViewModel

class LoginFactory(
    private val preferencesUseCase: PreferencesUseCase,
    private val userUseCase: UserUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                preferencesUseCase,
                userUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}