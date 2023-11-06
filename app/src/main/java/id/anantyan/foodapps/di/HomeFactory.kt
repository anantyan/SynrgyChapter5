package id.anantyan.foodapps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.presentation.home.HomeViewModel

class HomeFactory(private val preferencesUseCase: PreferencesUseCase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(preferencesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}