package com.compfest16.sea_salon.features.domain.model

import androidx.compose.ui.graphics.Color
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import com.compfest16.sea_salon.features.presentation.design_system.CompfestAqua
import com.compfest16.sea_salon.features.presentation.design_system.CompfestLightGrey
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPink
import com.compfest16.sea_salon.features.presentation.design_system.CompfestPurple
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

    fun getHistoryColor(): Color{
        return when(reservationType){
            1 -> CompfestPurple
            2 -> CompfestAqua
            3 -> CompfestPink
            else -> CompfestLightGrey
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
