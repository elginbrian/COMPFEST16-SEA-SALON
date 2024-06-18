package com.compfest16.sea_salon.features.data.repository

import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl: UserRepository {
    override suspend fun GetUsers(): Flow<List<UserModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetUserByID(id: String): Flow<UserModel> {
        TODO("Not yet implemented")
    }

    override suspend fun PostUser(user: UserModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun PutUser(user: UserModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun DeleteUser(id: String): Flow<String> {
        TODO("Not yet implemented")
    }
}