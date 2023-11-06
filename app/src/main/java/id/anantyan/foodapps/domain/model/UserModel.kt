package id.anantyan.foodapps.domain.model

import id.anantyan.foodapps.data.local.entities.UserEntity

data class UserModel(
    val id: Long? = null,
    val username: String? = null,
    val email: String? = null,
    val password: String? = null
)

fun UserEntity.toModel(): UserModel {
    return UserModel(id, username, email)
}

fun UserModel.toEntity(): UserEntity {
    return UserEntity(0, username, email, password)
}

fun UserModel.toEntityUpdate(): UserEntity {
    return UserEntity(id ?: -1L, username, email, password)
}