package com.compfest16.sea_salon.features.presentation.screen.review_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ReservationModel
import com.compfest16.sea_salon.features.domain.model.ReviewModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.ReservationRepository
import com.compfest16.sea_salon.features.domain.repository.ReviewRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.launch

class ReviewViewModel(
    val userRepository: UserRepository,
    val branchRepository: BranchRepository,
    val reservationRepository: ReservationRepository,
    val reviewRepository: ReviewRepository
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

    fun postReview(reviewModel: ReviewModel){
        viewModelScope.launch {
            reviewRepository.PostReview(reviewModel).collect{
                Log.d("ReviewViewModel", "postReview: $it")
            }
        }
    }
}