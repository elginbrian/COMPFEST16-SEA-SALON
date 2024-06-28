package com.compfest16.sea_salon.features.presentation.screen.dashboard_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import com.compfest16.sea_salon.features.domain.repository.ReviewRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val reservationRepository: ReservationRepository,
    private val reviewRepository: ReviewRepository,
    private val userRepository: UserRepository,
    private val branchRepository: BranchRepository,
    private val imageRepository: ImageRepository
): ViewModel() {
    fun getAllReservation(
        onResult: (List<ReservationModel>) -> Unit
    ){
        viewModelScope.launch {
            reservationRepository.GetReservations().collect{
                Log.d("DashboardViewModel", "getAllReservation: $it")
                onResult(it)
            }
        }
    }

    fun getAllReviews(
        onResult: (List<ReviewModel>) -> Unit
    ){
        viewModelScope.launch {
            reviewRepository.GetReviews().collect {
                Log.d("DashboardViewModel", "getAllReviews: $it")
                onResult(it)
            }
        }
    }

    fun getAllUsers(
        onResult: (List<UserModel>) -> Unit
    ){
        viewModelScope.launch {
            userRepository.GetUsers().collect {
                Log.d("DashboardViewModel", "getAllUsers: $it")
                onResult(it)
            }
        }
    }

    fun postBranch(
        branch: BranchModel,
        image: ImageModel,
        onResult: (String) -> Unit
    ){
        viewModelScope.launch {
            branchRepository.PostBranch(branch).collect{
                Log.d("DashboardViewModel", "postBranch: $it")
                onResult(it)

                imageRepository.PostImage(image).collect{
                    Log.d("DashboardViewModel", "postImage: $it")
                    onResult(it)
                }
            }
        }
    }
}