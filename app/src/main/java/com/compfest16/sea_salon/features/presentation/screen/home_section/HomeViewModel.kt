package com.compfest16.sea_salon.features.presentation.screen.home_section

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.model.ImageModel
import com.compfest16.sea_salon.features.domain.model.UserModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.compfest16.sea_salon.features.domain.repository.ImageRepository
import com.compfest16.sea_salon.features.domain.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    val userRepository: UserRepository,
    val branchRepository: BranchRepository,
    val imageRepository: ImageRepository
): ViewModel() {
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
}