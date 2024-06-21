package com.compfest16.sea_salon.features.presentation.screen.test_section

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compfest16.sea_salon.features.domain.dummy.BranchDummy
import com.compfest16.sea_salon.features.domain.model.BranchModel
import com.compfest16.sea_salon.features.domain.repository.BranchRepository
import kotlinx.coroutines.launch

class TestViewModel(
    private val branchRepository: BranchRepository
): ViewModel() {
    fun postBranch(branch: BranchModel){
        viewModelScope.launch {
            branchRepository.PostBranch(branch).collect{

            }
        }
    }

}