package id.anantyan.foodapps.data.local.repository

import android.util.Log
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.local.dao.UsersDao
import id.anantyan.foodapps.domain.model.UserModel
import id.anantyan.foodapps.domain.model.toEntity
import id.anantyan.foodapps.domain.model.toEntityUpdate
import id.anantyan.foodapps.domain.model.toModel
import id.anantyan.foodapps.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImpl(
    private val usersDao: UsersDao
) : UserRepository {
    override fun login(user: UserModel): Flow<UIState<UserModel>> {
        return flow {
            val result = usersDao.login(user.email, user.password)
            if (result != null) {
                emit(UIState.Success(result.toModel()))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_login))
            }
        }
    }

    override fun register(user: UserModel): Flow<UIState<Int>> {
        return flow {
            val result = usersDao.duplicateUser(user.email, user.username)
            if (result == null) {
                val registered = usersDao.register(user.toEntity())
                if (registered != 0L) {
                    emit(UIState.Success(R.string.txt_success_register))
                } else {
                    emit(UIState.Error(null, R.string.txt_invalid_register))
                }
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_register))
            }
        }
    }

    override fun profile(id: Long?): Flow<UIState<UserModel>> {
        return flow {
            val result = usersDao.profile(id)
            if (result != null) {
                emit(UIState.Success(result.toModel()))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_profile))
            }
        }
    }

    override fun changeProfile(user: UserModel): Flow<UIState<Int>> {
        return flow {
            val result = usersDao.changeProfile(user.toEntityUpdate())
            if (result != 0) {
                emit(UIState.Success(R.string.txt_success_change_profile))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_change_profile))
            }
        }
    }
}