package com.compfest16.sea_salon.features.data.repository

import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import kotlinx.coroutines.flow.Flow

class BranchRepositoryImpl: BranchRepository {
    override suspend fun GetBranches(): Flow<List<BranchModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun GetBranchByID(id: String): Flow<BranchModel> {
        TODO("Not yet implemented")
    }

    override suspend fun PostBranch(branch: BranchModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun PutBranch(branch: BranchModel): Flow<String> {
        TODO("Not yet implemented")
    }

    override suspend fun DeleteBranch(id: String): Flow<String> {
        TODO("Not yet implemented")
    }
}