package com.compfest16.sea_salon.features.domain.repository

import com.compfest16.sea_salon.features.domain.model.BranchModel
import kotlinx.coroutines.flow.Flow

interface BranchRepository {
    suspend fun GetBranches(): Flow<List<BranchModel>>
    suspend fun GetBranchByID(id: String): Flow<BranchModel>
    suspend fun PostBranch(branch: BranchModel): Flow<String>
    suspend fun PutBranch(branch: BranchModel): Flow<String>
    suspend fun DeleteBranch(id: String): Flow<String>
}