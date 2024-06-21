package com.compfest16.sea_salon.features.presentation.screen.nearby_section

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NearbyViewModel(
    private val branchRepository: BranchRepository
): ViewModel() {
    val _selectedCoordinates = MutableStateFlow<LatLng?>(LatLng(0.0, 0.0))
    val selectedCoordinates = _selectedCoordinates.asStateFlow()
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
}