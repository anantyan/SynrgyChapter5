package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.local.entities.UserEntity
import id.anantyan.foodapps.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

class UserUseCase(private val userRepository: UserRepository) {
    fun executeLogin(user: UserModel): Flow<UIState<UserModel>> = userRepository.login(user)
    fun executeRegister(user: UserModel): Flow<UIState<Int>> = userRepository.register(user)
    fun executeProfile(id: Long?): Flow<UIState<UserModel>> = userRepository.profile(id)
    fun executeChangeProfile(user: UserModel): Flow<UIState<Int>> = userRepository.changeProfile(user)
}