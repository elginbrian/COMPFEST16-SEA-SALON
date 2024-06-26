package com.compfest16.sea_salon.features.presentation.screen.home_section

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

class HomeViewModel(
    private val userRepository: UserRepository,
    private val branchRepository: BranchRepository,
    private val imageRepository: ImageRepository,
    private val reservationRepository: ReservationRepository,
    private val reviewRepository: ReviewRepository
): ViewModel() {
    fun getBranchList(
        onResult: (List<BranchModel>) -> Unit
    ){
        viewModelScope.launch {
            branchRepository.GetBranches().collect{
                Log.d("NearbyViewModel", "getNearbyBranches: $it")
                onResult(it)
            }
        }
    }

    fun getUserHistory(
        affiliate: String,
        onResult: (List<ReservationModel>) -> Unit
    ){
        viewModelScope.launch {
            reservationRepository.GetReservationByUserID(affiliate).collect{
                Log.d("HomeViewModel", "getUserHistory: $it")
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

    fun getImageByAffiliate(
        affiliate: String,
        role: String,
        onResult: (List<ImageModel>) -> Unit
    ){
        viewModelScope.launch {
            imageRepository.GetImageById(affiliate, role).collect{
                onResult(it)
            }
        }
    }

    fun getUserReview(
        affiliate: String,
        onResult: (List<ReviewModel>) -> Unit
    ){
        viewModelScope.launch {
            reviewRepository.GetReviewByUserID(affiliate).collect{
                onResult(it)
            }
        }
    }
}