package com.compfest16.sea_salon.features.presentation.screen.reservation_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val branchRepository: BranchRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun getNearbyBranches(
        onResult: (List<BranchModel>) -> Unit
    ){
        viewModelScope.launch {
            branchRepository.GetBranches().collect{
                Log.d("NearbyViewModel", "getNearbyBranches: $it")
                onResult(it)
            }
        }
    }

    fun bookReservation(
        reservation: ReservationModel,
        onResult: (String) -> Unit
    ){
        viewModelScope.launch {
            reservationRepository.PostReservation(reservation).collect{
                Log.d("ReservationViewModel", "bookReservation: $it")
                onResult(it)
            }
        }
    }

    fun getUserByEmail(
        email: String,
        onResult: (UserModel) -> Unit
    ){
        viewModelScope.launch {
            userRepository.GetUserByEmail(email).collect{
                onResult(it)
            }
        }
    }
}