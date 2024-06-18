package com.compfest16.sea_salon.features.domain.model

import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import java.util.UUID

data class ReviewModel(
    var reviewID: String = UUID.randomUUID().toString(),
    var branchID: String,
    var userID: String,
    var star: Int,
    var comment: String
){
    suspend fun getUser(
        repository: UserRepository,
        onSuccess: (UserModel) -> Unit
    ){
        repository.GetUserByID(userID).collect{
            onSuccess(it)
        }
    }

    suspend fun getBranch(
        repository: BranchRepository,
        onSuccess: (BranchModel) -> Unit
    ){
        repository.GetBranchByID(branchID).collect{
            onSuccess(it)
        }
    }
}
