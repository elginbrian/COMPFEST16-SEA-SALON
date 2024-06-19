package com.compfest16.sea_salon.features.domain.repository

import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun SignUpUser(user: UserModel, image: ImageModel): Flow<String>
    suspend fun GetUsers(): Flow<List<UserModel>>
    suspend fun GetUserByID(id: String): Flow<UserModel>
    suspend fun PostUser(user: UserModel): Flow<String>
    suspend fun PutUser(user: UserModel): Flow<String>
    suspend fun DeleteUser(id: String): Flow<String>
}