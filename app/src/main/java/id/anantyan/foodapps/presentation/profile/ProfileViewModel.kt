package id.anantyan.foodapps.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import id.anantyan.foodapps.domain.repository.UserUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import java.lang.Thread.State

class ProfileViewModel(
    private val preferencesUseCase: PreferencesUseCase,
    private val userUseCase: UserUseCase
) : ViewModel() {
    private var _showProfile: MutableStateFlow<UIState<List<ProfileItemModel>>> = MutableStateFlow(UIState.Loading())

    val showProfile: StateFlow<UIState<List<ProfileItemModel>>> = _showProfile

    @OptIn(ExperimentalCoroutinesApi::class)
    fun showProfile() {
        viewModelScope.launch {
            preferencesUseCase.executeGetUserId().flatMapLatest {
                userUseCase.executeProfile(it)
            }.collect { state ->
                _showProfile.value = when (state) {
                    is UIState.Loading -> { UIState.Loading() }
                    is UIState.Success -> {
                        UIState.Success(
                            listOf(
                                ProfileItemModel(R.drawable.ic_key_id, "ID", state.data?.id.toString()),
                                ProfileItemModel(R.drawable.ic_shield_person, "Username", state.data?.username),
                                ProfileItemModel(R.drawable.ic_email, "Email", state.data?.email)
                            )
                        )
                    }
                    is UIState.Error -> { UIState.Error(null, state.message!!) }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            preferencesUseCase.executeSetUsrId(-1L)
            preferencesUseCase.executeSetLogin(false)
        }
    }
}