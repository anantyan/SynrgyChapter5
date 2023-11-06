package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.local.entities.UserEntity
import id.anantyan.foodapps.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun login(user: UserModel): Flow<UIState<UserModel>>
    fun register(user: UserModel): Flow<UIState<Int>>
    fun profile(id: Long?): Flow<UIState<UserModel>>
    fun changeProfile(user: UserModel): Flow<UIState<Int>>
}