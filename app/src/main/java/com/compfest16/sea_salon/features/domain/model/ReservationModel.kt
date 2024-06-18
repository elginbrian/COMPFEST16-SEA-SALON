package com.compfest16.sea_salon.features.domain.model

import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import java.util.UUID

data class ReservationModel(
    val reservationID: String = UUID.randomUUID().toString(),
    val branchID: String,
    val userID: String,
    val reservationType: Int,
    val date: String
){
    fun getReservationType(): String{
        return when(reservationType){
            1 -> "Haircuts and styling"
            2 -> "Manicure and pedicure"
            3 -> "Facial treatments"
            else -> "Custom"
        }
    }
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
