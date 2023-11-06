package id.anantyan.foodapps.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(
    private val preferencesUseCase: PreferencesUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {

    private var _login: MutableStateFlow<UIState<UserModel>> = MutableStateFlow(UIState.Loading())

    val login: StateFlow<UIState<UserModel>> = _login
    val getTheme: Flow<Boolean> = preferencesUseCase.executeGetTheme()

    fun login(user: UserModel) {
        viewModelScope.launch {
            userUseCase.executeLogin(user).collect { state ->
                _login.value = when (state) {
                    is UIState.Loading -> {
                        UIState.Loading()
                    }
                    is UIState.Success -> {
                        preferencesUseCase.executeSetLogin(true)
                        preferencesUseCase.executeSetUsrId(state.data?.id ?: -1)
                        UIState.Success(state.data ?: UserModel())
                    }
                    is UIState.Error -> {
                        UIState.Error(null, state.message!!)
                    }
                }
            }
        }
    }

    fun setTheme(value: Boolean) {
        viewModelScope.launch { preferencesUseCase.executeSetTheme(value) }
    }
}